package com.mall.user_center.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogInDTO {
    /**
     * 编码
     */
    private String code;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     * 微信昵称
     */
    private String wxNickName;
}
