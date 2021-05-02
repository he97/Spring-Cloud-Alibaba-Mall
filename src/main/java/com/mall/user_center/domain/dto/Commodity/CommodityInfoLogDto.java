package com.mall.user_center.domain.dto.Commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class CommodityInfoLogDto {
    private String logId;

    private String commodityId;

    private String log;

    private String transactionId;

    private Date time;

    private String buyerId;
}
