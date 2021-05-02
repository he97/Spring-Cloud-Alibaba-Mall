package com.mall.user_center.domain.dto.Commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SelectedCommodityDTO {
    private String list;

    private String token;
}
