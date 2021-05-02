package com.mall.user_center.domain.dto.alterDTO;

import com.alibaba.fastjson.JSONObject;
import com.mall.user_center.domain.enums.OrderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private String userId;

    private String commodityId;

    private String orderId;

    private Integer orderPrice;

    private Date orderDate;

    private JSONObject orderInfo;

    private String brandId;

    private OrderEnum orderEnum;
}
