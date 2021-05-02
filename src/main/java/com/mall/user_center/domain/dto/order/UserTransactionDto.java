package com.mall.user_center.domain.dto.order;

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

public class UserTransactionDto {
    private List<CartCommoditiesDTO> commodities;

    private String status;

    private Float price;

    private String info;

    private String phone;

    private String address;

    private String name;
}
