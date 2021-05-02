package com.mall.user_center.domain.entity.user_center;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "transaction_log")
public class TransactionLog {
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(generator = "JDBC")
    private String transactionId;

    @Column(name = "user_id")
    private String userId;

    private String log;

    @Column(name = "transaction_status")
    private String transactionStatus;

    private Date time;
}