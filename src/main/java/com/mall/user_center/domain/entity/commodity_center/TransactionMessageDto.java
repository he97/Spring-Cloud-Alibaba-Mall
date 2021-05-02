package com.mall.user_center.domain.entity.commodity_center;

import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionMessageDto {

    private List<CartCommoditiesDTO> commodities;

    private String status;

    private String buyerId;

    private String transactionId;
}
