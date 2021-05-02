package com.mall.user_center.domain.dto.Commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CartCommoditiesDTO {
    private String commodityId;

    private String brandId;

    private String commodityName;

    private String commodityCategory;

    private String commodityInformation;

    private Integer commodityRemainder;

    private String commodityFirstPictureUrl;

    private Float price;

    private Integer count;

    private Boolean cloud;
}
