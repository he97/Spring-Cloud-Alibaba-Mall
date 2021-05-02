package com.mall.user_center.domain.dto.adminDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Data@AllArgsConstructor@Builder@NoArgsConstructor
public class UserRespDto {
    private String userId;

    private String userName;

    private Boolean userValid;

    private String weixinAvatuaurl;
}
