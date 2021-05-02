package com.mall.user_center.domain.entity.user_center;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "user")
public class User {
        @Id
        @Column(name = "user_id")
        @GeneratedValue(generator = "JDBC")
        private String userId;

        @Column(name = "user_valid")
        private Boolean userValid;

        @Column(name = "user_password")
        private String userPassword;

        @Column(name = "default_address_id")
        private String defaultAddressId;

        @Column(name = "user_status")
        private String userStatus;
}