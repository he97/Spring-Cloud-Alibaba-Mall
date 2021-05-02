package com.mall.user_center.domain.dto.Commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommodityRespDto {

    private String commodityId;

    private String brandId;

    private String brandName;

    private String commodityName;

    private String categoryName;

    private String commodityCategory;

    private String commodityInformation;

    private Integer commodityRemainder;

    private List<String> commodityPictureUrl;

    private Float price;

    private Boolean cloud;
}
