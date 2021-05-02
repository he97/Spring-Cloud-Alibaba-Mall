package com.mall.user_center.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MySource {

    String OUTPUT = "output";

    String ALTER_TRANSACTION = "alterTransaction";

    @Output(OUTPUT)
    MessageChannel output();

    @Output(ALTER_TRANSACTION)
    MessageChannel alterTransaction();
}
