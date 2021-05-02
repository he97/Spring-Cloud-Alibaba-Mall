package com.mall.user_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class RespDto {
    private String message;

    private String status;
}
