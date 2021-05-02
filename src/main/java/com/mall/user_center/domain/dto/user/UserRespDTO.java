package com.mall.user_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRespDTO {
    private String brandId;

    private String wxNickName;

    private String avatarUrl;

    private String brandName;

    private String userId;

    private String userPassword;

    private String commodityId;

    private String buyingId;

    private Float buyingPrice;

    private Date buyingDate;

    private String buyingInfo;

}
