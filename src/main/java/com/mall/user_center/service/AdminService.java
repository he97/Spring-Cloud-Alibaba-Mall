package com.mall.user_center.service;

import com.alibaba.fastjson.JSONArray;
import com.mall.user_center.dao.user_center.*;
import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoRespDto;
import com.mall.user_center.domain.dto.Commodity.CommodityRespDto;
import com.mall.user_center.domain.dto.adminDto.SearchDto;
import com.mall.user_center.domain.dto.adminDto.UserRespDto;
import com.mall.user_center.domain.dto.transaction.BoughtCommodityInfoDto;
import com.mall.user_center.domain.dto.transaction.CancelTransactionDto;
import com.mall.user_center.domain.dto.transaction.TransactionCommodityDto;
import com.mall.user_center.domain.entity.user_center.TransactionList;
import com.mall.user_center.domain.entity.user_center.User;
import com.mall.user_center.domain.entity.user_center.WeixinInfo;
import com.mall.user_center.domain.enums.EnumToDescription;
import com.mall.user_center.domain.enums.TransactionCommodityMessageStatusEnum;
import com.mall.user_center.feignClient.CommodityCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AdminService {
    private final CommodityCenterFeignClient commodityCenterFeignClient;
    private final WeixinInfoMapper weixinInfoMapper;
    private final UserInformationMapper userInformationMapper;
    private final UserMapper userMapper;
    private final TransactionListMapper transactionListMapper;
    private final EnumToDescription enumToDescription;
    private UserAndCommodityService userAndCommodityService;

    public ArrayList<CartCommoditiesDTO> getAllRequiredCommodities() {
        List<CommodityRespDto> requiredCheckCommodities = this.commodityCenterFeignClient.getRequiredCheckCommodities();
        ArrayList<CartCommoditiesDTO> cartCommoditiesDTOArrayList = new ArrayList<>();
        for (CommodityRespDto commodityRespDto :
                requiredCheckCommodities) {
            CartCommoditiesDTO cartCommoditiesDTO = new CartCommoditiesDTO();
            BeanUtils.copyProperties(commodityRespDto,cartCommoditiesDTO);
            cartCommoditiesDTO.setCommodityFirstPictureUrl(commodityRespDto.getCommodityPictureUrl().get(0));
            cartCommoditiesDTOArrayList.add(cartCommoditiesDTO);
        }
        return cartCommoditiesDTOArrayList;
    }

    public List<UserRespDto> getAllUser() {
        List<User> users = this.userMapper.selectAll();
        ArrayList<UserRespDto> userRespDtos = new ArrayList<>();
        users.forEach(dto ->{
            UserRespDto userRespDto = new UserRespDto();
            BeanUtils.copyProperties(dto,userRespDto);
            Example example = new Example(WeixinInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId",dto.getUserId());
            String weixinAvatuaurl = this.weixinInfoMapper.selectOneByExample(example).getWeixinAvatuaurl();
            userRespDto.setWeixinAvatuaurl(weixinAvatuaurl);
            userRespDto.setUserName(
                    this.userInformationMapper.selectByPrimaryKey(dto.getUserId()).getUserName()
            );
            userRespDtos.add(userRespDto);

        });
        return userRespDtos;
    }

    public List<BoughtCommodityInfoDto> getAllTransaction() {
        List<TransactionList> transactionLists = this.transactionListMapper.selectAll();
        return  UserAndCommodityService.getBoughtCommodityInfoDtos(transactionLists,this.enumToDescription);
    }

    public List<CommodityInfoRespDto> getAllCommodities() {
        return this.commodityCenterFeignClient.getAllCommodity();
    }

    public List<CommodityRespDto> searchCommodity(SearchDto searchDto) {
        return this.commodityCenterFeignClient.searchOneCommodityById(searchDto);
    }

    /**
     * 获取正在纠纷中的订单
     * @return
     */
    public List<BoughtCommodityInfoDto> getInDispute() {
        List<TransactionList> transactionLists = this.transactionListMapper.selectAll();
        return getBoughtCommodityInfoDtos(transactionLists,this.enumToDescription);
    }
    public static ArrayList<BoughtCommodityInfoDto> getBoughtCommodityInfoDtos(List<TransactionList> transactionLists, EnumToDescription enumToDescription) {
        ArrayList<BoughtCommodityInfoDto> boughtCommodityInfoDtos = new ArrayList<>();
        for (TransactionList transaction :
                transactionLists) {
            List<BoughtCommodityInfoDto> cartCommoditiesDtoS = JSONArray.parseArray(transaction.getCommodities(), BoughtCommodityInfoDto.class);
            cartCommoditiesDtoS.forEach(dto -> {
                if(dto.getCommodityStatus().equals(TransactionCommodityMessageStatusEnum.USER_REJECT_CANCEL.name())){
                    dto.setTransactionId(transaction.getTransactionId());
                    dto.setCommodityStatus(enumToDescription.getTCMSDesc(
                            dto.getCommodityStatus()
                    ));
                    boughtCommodityInfoDtos.add(dto);
                }
            });

        }
        return boughtCommodityInfoDtos;
    }

}
