package com.mall.user_center.domain.dto.Commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor

@NoArgsConstructor
@Builder
public class HadBoughtInfoRespDto {
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

    private String transactionId;

    private String userId;

    private Integer transactionPrice;

    private String transactionInfo;

    private Date transactionTime;

    private String transactionPhone;

    private String transactionAddress;

}
