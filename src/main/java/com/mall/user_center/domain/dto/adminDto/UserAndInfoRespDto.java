package com.mall.user_center.domain.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class UserAndInfoRespDto {
    private String userId;

    private Boolean userValid;

    private String defaultAddressId;

    private String userStatus;

    private Integer userAge;

    private Date userBirthdate;

    private String userSex;

    private Float userBalance;

    private String userName;

    private String userPhone;
}
