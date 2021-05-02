package com.mall.user_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoAndBrandDTO {
    private String brandId;

    private String brandName;

    private Integer userId;

    private String userPassword;

    private String commodityId;

    private String buyingId;

    private Float buyingPrice;

    private Date buyingDate;

    private String buyingInfo;

}
