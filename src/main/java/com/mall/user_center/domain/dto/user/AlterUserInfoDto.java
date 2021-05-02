package com.mall.user_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class AlterUserInfoDto {
    private Date userBirthdate;

    private String userSex;

    private String userName;

    private String userPhone;
}
