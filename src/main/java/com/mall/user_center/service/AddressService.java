package com.mall.user_center.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.mall.user_center.dao.user_center.UserAddressMapper;
import com.mall.user_center.dao.user_center.UserMapper;
import com.mall.user_center.domain.dto.address.AddressDto;
import com.mall.user_center.domain.dto.address.AddressRespDto;
import com.mall.user_center.domain.entity.user_center.UserAddress;
import com.mall.user_center.domain.enums.CommodityEnum;
import com.mall.user_center.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AddressService {
    private final JwtOperator jwtOperator;
    private final UserAddressMapper userAddressMapper;
    private final UserMapper userMapper;

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

    /**
     * 添加用户地址
     * @param addressDto
     * @return
     */
    public AddressRespDto addAddress(AddressDto addressDto) {
        try {
            String token = this.getToken();
            String tokenId = this.getTokenId(token);
//        为地址设置一个随机的ID
            UUID uuid = UUID.randomUUID();
//        获取用户地址实例
            UserAddress userAddress = this.userAddressMapper.selectByPrimaryKey(tokenId);
            String userPlace = userAddress.getUserPlace();
//        转化为list
            List<AddressDto> addressList = JSONArray.parseArray(userPlace).toJavaList(AddressDto.class);

//            循环 看有没有重复的
            for (AddressDto address :
                    addressList) {
//              有重复的
                if (addressDto.getAddressAccurateAddress().equals(address.getAddressAccurateAddress()) &&
                        addressDto.getAddressName().equals(address.getAddressName()) &&
                                addressDto.getAddressCity().equals(address.getAddressCity()) &&
                                        addressDto.getAddressPhone().equals(address.getAddressPhone())){
                    throw new IllegalStateException("此地址已经存在了");
                }
            }
            addressDto.setAddressId(uuid.toString());
            addressDto.setUserId(tokenId);
//        String toJSONString = JSONObject.toJSONString(addressDto);
            addressList.add(addressDto);
            String updatedAddress = JSONObject.toJSONString(addressList);
            UserAddress newAddress = UserAddress.builder()
                    .userId(tokenId)
                    .userPlace(updatedAddress)
                    .build();
            this.userAddressMapper.updateByPrimaryKey(newAddress);
        } catch (Exception e) {
            e.printStackTrace();
            return AddressRespDto.builder()
                    .status("500")
                    .message(e.getMessage())
                    .build();

        }
        return AddressRespDto.builder()
                .status("200")
                .message("ok")
                .build();

    }

    public List<AddressDto> getUserAddress() {
        String token = this.getToken();
        String tokenId = this.getTokenId(token);
        UserAddress userAddress = this.userAddressMapper.selectByPrimaryKey(tokenId);
        return JSONArray.parseArray(userAddress.getUserPlace(), AddressDto.class);
    }

    public AddressDto getDefaultAddress(String tokenId) {
        String defaultAddressId = this.userMapper.selectByPrimaryKey(tokenId).getDefaultAddressId();
        UserAddress userAddress = this.userAddressMapper.selectByPrimaryKey(tokenId);
        List<AddressDto> addressDtos = JSONArray.parseArray(userAddress.getUserPlace(), AddressDto.class);
        AddressDto addressDto = new AddressDto();
        for (AddressDto add :
                addressDtos) {
            if (add.getAddressId().equals(defaultAddressId)) {
                addressDto = add;
            }
        }
        return addressDto;

    }

    public AddressDto getAddressById(String addressId) {
        String token = this.getToken();
        String tokenId = this.getTokenId(token);
        if (addressId.isEmpty()){
            AddressDto defaultAddress = this.getDefaultAddress(tokenId);
            return defaultAddress;
        }
        UserAddress userAddress = this.userAddressMapper.selectByPrimaryKey(tokenId);
        List<AddressDto> addressDtos = JSONArray.parseArray(userAddress.getUserPlace(), AddressDto.class);
        AddressDto addressDto = new AddressDto();
        for (AddressDto add :
                addressDtos) {
            if (add.getAddressId().equals(addressId)) {
                addressDto = add;
            }
        }
        return addressDto;
    }

    public AddressRespDto alterAddress(AddressDto addressDto) {
        try {
            String userId = getUserId();
            List<AddressDto> userAddresses = this.getUserAddress();
            for (AddressDto address :
                    userAddresses) {
                if (addressDto.getAddressId().equals(address.getAddressId())) {
                    BeanUtils.copyProperties(addressDto,address,"addressId");
                }
            }
//        更新表
            UserAddress userAddress = this.userAddressMapper.selectByPrimaryKey(userId);
            userAddress.setUserPlace(JSONArray.toJSONString(userAddresses));
            this.userAddressMapper.updateByPrimaryKey(userAddress);
            return AddressRespDto.builder()
                    .message("更改地址完成")
                    .status("200")
                    .build();
        } catch (BeansException e) {
            e.printStackTrace();
            return AddressRespDto.builder()
                    .message("更改地址失败")
                    .status("500")
                    .build();
        }
    }

    private String getUserId() {
        String token = this.getToken();
        String tokenId = this.getTokenId(token);
        return tokenId;
    }
}
