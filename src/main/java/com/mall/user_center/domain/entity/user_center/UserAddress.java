package com.mall.user_center.domain.entity.user_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "user_address")
public class UserAddress {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "JDBC")
    private String userId;

    @Column(name = "user_place")
    private String userPlace;
}