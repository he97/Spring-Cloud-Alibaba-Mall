package com.mall.user_center.domain.dto.Brand;

import lombok.*;

import javax.persistence.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private String brandId;

    private String brandName;
}
