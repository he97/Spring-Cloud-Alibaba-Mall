package com.mall.user_center.service;

import com.mall.user_center.dao.user_center.TransactionListMapper;
import com.mall.user_center.dao.user_center.UserMapper;
import com.mall.user_center.domain.dto.Brand.BrandDTO;
import com.mall.user_center.domain.dto.user.UserInfoAndBrandDTO;
import com.mall.user_center.domain.entity.user_center.TransactionList;
import com.mall.user_center.feignClient.CommodityCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserInfoAndBrandService {
    private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private final TransactionListMapper transactionListMapper;
    private final CommodityCenterFeignClient commodityCenterFeignClient;

    /**
     * @param primaryKey 主键的值 分别为user_id,commodity_id,buying_id
     * @return
     */
//    public UserInfoAndBrandDTO findUserInfoAndBrand(Integer primaryKey) {
////        Map<String,Integer> mapKeys = new HashMap();
////        mapKeys.put("user_id",1);
////        mapKeys.put("commodity_id",1);
////        mapKeys.put("buying_id",1);
////        log.info("mapKeys:{}",mapKeys);
//        TransactionList buyingList = transactionListMapper.selectByPrimaryKey(primaryKey);
//        log.info("this is primaryKey:{},this is result:{}", primaryKey, buyingList);
////        UserInformation userInformation = userInformationMapper.selectByPrimaryKey(userId);
//        String brandId = buyingList.getCommodityId();
//        log.info("this is brandId:{}", brandId);
//
//        BrandDTO brandDTO = this.commodityCenterFeignClient.findById(brandId);
////        System.out.println(userDTO);
////        System.out.println("rnm");
//        log.info("this is buying_list:{}", buyingList);
//        log.info("this is brandDto:{}", brandDTO);
//        UserInfoAndBrandDTO userInfoAndBrandDTO = new UserInfoAndBrandDTO();
//        BeanUtils.copyProperties(buyingList, userInfoAndBrandDTO);
//        System.out.println("第一次copy:" + userInfoAndBrandDTO);
//
//        BeanUtils.copyProperties(brandDTO, userInfoAndBrandDTO);
//        System.out.println("第二次copy:" + userInfoAndBrandDTO);
//        return userInfoAndBrandDTO;
//    }

}
