package com.mall.user_center.domain.dto.transaction;

import com.mall.user_center.domain.dto.Commodity.CommodityInfoRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.signature.qual.BinaryNameInUnnamedPackage;

@Data@Builder@AllArgsConstructor@NoArgsConstructor
public class BoughtCommodityInfoDto {
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

    private String transactionId;

    private String commodityStatus;

    private String ownerId;
}
