package com.mall.user_center.domain.entity.user_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "weixin_info")
public class WeixinInfo {
    @Id
    @Column(name = "weixin_code")
    @GeneratedValue(generator = "JDBC")
    private String weixinCode;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "weixin_nickname")
    private String weixinNickname;

    @Column(name = "weixin_avatuaurl")
    private String weixinAvatuaurl;
}