package com.mall.user_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoRespDto {
    private Integer userAge;

    private String userBirthdate;

    private String userSex;

    private Float userBalance;

    private String userName;

    private String userPhone;

    private Boolean valid;

    private String status;

    private String message;
}
