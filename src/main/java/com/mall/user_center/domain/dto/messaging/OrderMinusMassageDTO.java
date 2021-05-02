package com.mall.user_center.domain.dto.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMinusMassageDTO {
    private String orderId;

    private String userId;

    private String commodityId;
}
