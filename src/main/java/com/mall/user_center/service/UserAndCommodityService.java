package com.mall.user_center.service;

import com.alibaba.fastjson.JSONArray;
import com.mall.user_center.dao.user_center.TransactionListMapper;
import com.mall.user_center.domain.dto.Commodity.*;
//import com.mall.user_center.domain.dto.Commodity.HadSelledRespDto;
import com.mall.user_center.domain.dto.transaction.BoughtCommodityInfoDto;
import com.mall.user_center.domain.dto.transaction.TransactionCommodityDto;
import com.mall.user_center.domain.entity.user_center.TransactionList;
import com.mall.user_center.domain.enums.EnumToDescription;
import com.mall.user_center.feignClient.CommodityCenterFeignClient;
import com.mall.user_center.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserAndCommodityService {
    private final JwtOperator jwtOperator;
    private final TransactionListMapper transactionListMapper;
    private final CommodityCenterFeignClient commodityCenterFeignClient;
    private final EnumToDescription enumToDescription;

    /**
     * 从token中获取id
     * @param token
     * @return
     */
    private String getTokenId(String token) {
        Claims claimsFromToken = this.jwtOperator.getClaimsFromToken(token);
        String tokenId;
        tokenId = (String) claimsFromToken.get("id");
        return tokenId;
    }

    private String getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader("X-Token");
    }

//    public List<CartCommoditiesDTO> getUserCommit() {
//        String token = this.getToken();
//        String tokenId = this.getTokenId(token);
//        Example example = new Example();
//        Example.Criteria criteria = example.createCriteria();
//        criteria.
//
//    }

    public List<BoughtCommodityInfoDto> getUserBought() {
        List<TransactionList> transactionLists = getLists();
        return getBoughtCommodityInfoDtos(transactionLists,this.enumToDescription);
    }

    public static ArrayList<BoughtCommodityInfoDto> getBoughtCommodityInfoDtos(List<TransactionList> transactionLists, EnumToDescription enumToDescription) {
        ArrayList<BoughtCommodityInfoDto> cartCommoditiesDtoArrayList = new ArrayList<>();
        for (TransactionList transaction :
                transactionLists) {
            List<BoughtCommodityInfoDto> cartCommoditiesDtoS = JSONArray.parseArray(transaction.getCommodities(), BoughtCommodityInfoDto.class);
            cartCommoditiesDtoS.forEach(dto -> {
                dto.setTransactionId(transaction.getTransactionId());
                dto.setCommodityStatus(enumToDescription.getTCMSDesc(
                        dto.getCommodityStatus()
                ));
            });
            cartCommoditiesDtoArrayList.addAll(cartCommoditiesDtoS);
        }
        return cartCommoditiesDtoArrayList;
    }

    /**
     * 获取用户transactionList
     * @return
     */
    private List<TransactionList> getLists() {
        String token = this.getToken();
        String tokenId = this.getTokenId(token);

        Example example = new Example(TransactionList.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",tokenId);
//        获取所有List
        List<TransactionList> transactionLists = this.transactionListMapper.selectByExample(example);
        log.info("总共有多少笔订单：{}",transactionLists.size());
        return transactionLists;
    }

    public HadBoughtInfoRespDto getUserBoughtOrSellInfo(HadBoughtInfoDto hadBoughtInfoDto) {
        HadBoughtInfoRespDto boughtInfoRespDto = new HadBoughtInfoRespDto();
        List<TransactionList> lists = this.getLists();
        for (TransactionList transaction :
                lists) {
            List<CartCommoditiesDTO> cartCommoditiesDTOS = JSONArray.parseArray(transaction.getCommodities(), CartCommoditiesDTO.class);
            for (CartCommoditiesDTO cart :
                    cartCommoditiesDTOS) {
                if (cart.getCommodityId().equals(hadBoughtInfoDto.getCommodityId())) {
                    CommodityInfoRespDto commodityInfoById = this.commodityCenterFeignClient.getCommodityInfoById(hadBoughtInfoDto.getCommodityId());
                    BeanUtils.copyProperties(commodityInfoById,boughtInfoRespDto);
                    BeanUtils.copyProperties(transaction,boughtInfoRespDto);
                    return boughtInfoRespDto;
                }
            }
        }
        throw  new IllegalAccessError();
    }




    public List<BoughtCommodityInfoDto> getSell() {
        List<CommodityInfoLogDto> sell = this.commodityCenterFeignClient.getSell();
        List<BoughtCommodityInfoDto> boughtCommodityInfoDtos = new ArrayList<>();
        for (CommodityInfoLogDto dto : sell) {//            根据订单号找订单
            TransactionList transactionList = this.transactionListMapper.selectByPrimaryKey(dto.getTransactionId());
//            找到符合需要用户确认是否退货的商品
            List<TransactionCommodityDto> transactionCommodityDtos = JSONArray.parseArray(transactionList.getCommodities(), TransactionCommodityDto.class);
//            获取订单中符合条件的商品
            transactionCommodityDtos = transactionCommodityDtos.stream().filter(transactionCommodityDto ->
                    transactionCommodityDto.getCommodityId()
                            .equals(dto.getCommodityId())).collect(Collectors.toList());
            transactionCommodityDtos.forEach(tcd -> {
                BoughtCommodityInfoDto boughtCommodityInfoDto = new BoughtCommodityInfoDto();
                BeanUtils.copyProperties(tcd,boughtCommodityInfoDto);
                boughtCommodityInfoDto.setTransactionId(transactionList.getTransactionId());
                boughtCommodityInfoDto.setCommodityStatus(this.enumToDescription.getTCMSDesc(boughtCommodityInfoDto.getCommodityStatus()));
                boughtCommodityInfoDtos.add(boughtCommodityInfoDto);
            });
        }
        return boughtCommodityInfoDtos;
    }


}
