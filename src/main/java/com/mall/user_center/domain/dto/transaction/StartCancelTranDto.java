package com.mall.user_center.domain.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class StartCancelTranDto {

    private String commodityId;

    private String transactionId;
}
