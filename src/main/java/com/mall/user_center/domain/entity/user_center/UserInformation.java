package com.mall.user_center.domain.entity.user_center;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "user_information")
public class UserInformation {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator = "JDBC")
    private String userId;

    @Column(name = "user_age")
    private Integer userAge;

    @Column(name = "user_birthdate")
    private Date userBirthdate;

    @Column(name = "user_sex")
    private String userSex;

    @Column(name = "user_balance")
    private Float userBalance;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_phone")
    private String userPhone;
}