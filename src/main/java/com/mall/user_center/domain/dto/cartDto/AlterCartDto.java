package com.mall.user_center.domain.dto.cartDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class AlterCartDto {
    private String type;

    private String commodityId;
}
