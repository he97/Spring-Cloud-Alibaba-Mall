package com.mall.user_center.rocketmq;


import com.alibaba.fastjson.JSON;
import com.mall.user_center.dao.user_center.TransactionLogMapper;
import com.mall.user_center.domain.dto.alterDTO.OrderDTO;
import com.mall.user_center.domain.entity.user_center.TransactionLog;
import com.mall.user_center.service.HandleOrderService;
//import com.mall.user_center.service.OrderStockMinusService;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@RocketMQTransactionListener(txProducerGroup = "user-center-group")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class minusStock implements RocketMQLocalTransactionListener {
    private final TransactionLogMapper transactionLogMapper;
    private final HandleOrderService handleOrderService;

    /**
     * 没有收到确认
     * @param message
     * @param o
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
//        1，获取transactionid
        MessageHeaders messageHeaders = message.getHeaders();
//        2，获取userid
        String transactionId = (String) messageHeaders.get(RocketMQHeaders.TRANSACTION_ID);
        String  userId = (String) messageHeaders.get("userId");
        try {
            this.handleOrderService.auditByIdWithMqLog(userId, transactionId);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    /**
     * 没有收到确认执行的方法
     *
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        TransactionLog transactionLog = this.transactionLogMapper.selectByPrimaryKey(transactionId);
        if (transactionLog == null) {
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            return RocketMQLocalTransactionState.COMMIT;
        }
    }
}
