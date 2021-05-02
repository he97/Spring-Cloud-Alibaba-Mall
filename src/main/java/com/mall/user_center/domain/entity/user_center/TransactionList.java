package com.mall.user_center.domain.entity.user_center;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "transaction_list")
public class TransactionList {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(generator = "JDBC")
    private String transactionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "transaction_price")
    private Float transactionPrice;

    @Column(name = "transaction_info")
    private String transactionInfo;

    @Column(name = "transaction_time")
    private Date transactionTime;

    @Column(name = "transaction_phone")
    private String transactionPhone;

    @Column(name = "transaction_address")
    private String transactionAddress;

    /**
     * []
     */
    private String commodities;
}