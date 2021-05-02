package com.mall.user_center.domain.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {

    private String userId;

    private String addressId;

    private String addressName;

    private String addressPhone;

    private String addressCity;

    private String addressAccurateAddress;

}
