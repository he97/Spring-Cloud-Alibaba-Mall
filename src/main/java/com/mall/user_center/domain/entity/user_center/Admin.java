package com.mall.user_center.domain.entity.user_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "admin_id")
    @GeneratedValue(generator = "JDBC")
    private String adminId;

    @Column(name = "weixin_id")
    private String weixinId;
}