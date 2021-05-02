//package com.mall.user_center.service;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.BeanUtils;
//import com.mall.user_center.domain.dto.alterDTO.OrderDTO;
//import com.mall.user_center.domain.dto.messaging.OrderMinusMassageDTO;
//import com.mall.user_center.domain.enums.OrderEnum;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.apache.rocketmq.spring.support.RocketMQHeaders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Objects;
//import java.util.UUID;
//
///**
// * 通过处理订单消息（先完成订单，再来库存减一），然后通过rocketmq来将对应地库存表里的库存减一。
// */
//@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Slf4j
//public class OrderStockMinusService {
//    private final UserMapper userMapper;
//    private final RocketMQTemplate rocketMQTemplate;
//    private final OrderListMapper orderListMapper;
//    private final OrderLogMapper orderLogMapper;
//    private final Source source;
//
//    public UserInformation auditList(OrderDTO orderDTO) {
//        log.info("in auditList");
////       1 获取user,如果获取不到用户或者该订单处理过则抛出异常
//        log.info("这是orderDTO:{}", orderDTO);
//        User user = new User();
//        user = this.userMapper.selectByPrimaryKey(orderDTO.getUserId());
//        String order_id = orderDTO.getOrderId();
//        if (user == null) {
//            throw new IllegalArgumentException("没有该为用户");
//        } else if (!Objects.equals(OrderEnum.NOT_YET, orderDTO.getOrderEnum())) {
//            throw new IllegalArgumentException("该订单已经处理过了");
//        }
////        处理订单，将状态设为YES，并且将
////        orderDTO = handleOrder(orderDTO);
//
////       2 如果是YES,则发送半消息
//        if (OrderEnum.NOT_YET.equals(orderDTO.getOrderEnum())) {
//            String transactionId = UUID.randomUUID().toString();
////            发送半消息
//            this.source.output()
//                    .send(
//                            MessageBuilder
//                                    .withPayload(
//                                            OrderMinusMassageDTO.builder()
//                                                    .userId(orderDTO.getUserId())
//                                                    .commodityId(orderDTO.getCommodityId())
//                                                    .orderId(orderDTO.getOrderId())
//                                                    .build()
//                                    )
//                                    .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
//                                    .setHeader("order_id", order_id)
//                                    .setHeader("dto", JSONObject.toJSONString(orderDTO))
//                                    .build()
//                    );
////            this.rocketMQTemplate.sendMessageInTransaction(
////                    "my-group",
////                    "minus-stock",
////                    MessageBuilder
////                            .withPayload(
////                                OrderMinusMassageDTO.builder()
////                                    .userId(orderDTO.getUserId())
////                                    .commodityId(orderDTO.getCommodityId())
////                                    .orderId(orderDTO.getOrderId())
////                                    .build()
////                    )
////                            .setHeader(RocketMQHeaders.TRANSACTION_ID, transactionId)
////                            .setHeader("order_id",order_id)
////                            .build(),
////                    orderDTO
////            );
//        }
////        3 审核资源
////        auditOrderByDB(user,orderDTO);
////        3.向商品微服务里传订单并且商品数量减一
////        this.rocketMQTemplate.convertAndSend("order-info",
////                OrderMinusMassageDTO.builder()
////                        .userId(orderDTO.getUserId())
////                        .commodityId(orderDTO.getCommodityId())
////                        .orderId(orderDTO.getOrderId())
////                        .build()
////                    );
//
//        return null;
//    }
//
//    /**
//     * 根据数据库里的信息对其进行审核
//     * 审核成功后加上log
//     *
//     * @param userId
//     * @param orderDTO
//     */
//    @Transactional(rollbackFor = Exception.class)
//    public void auditOrderByDB(Integer userId, OrderDTO orderDTO) {
//        OrderList orderList = new OrderList();
//        BeanUtils.copyProperties(orderDTO, orderList, "orderInfo");
//        orderList.setOrderInfo(orderDTO.getOrderInfo().toString());
//        log.info("这是插入到orderList的对象：{}", orderList);
//        this.orderListMapper.updateByPrimaryKeySelective(orderList);
//        orderDTO.setOrderEnum(OrderEnum.YES);
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public void auditByIdWithMqLog(Integer userId, OrderDTO orderDTO, String transactionId) {
//        this.auditOrderByDB(userId, orderDTO);
//        this.orderLogMapper.insertSelective(
//                OrderLog.builder()
//                        .log("成功完成事务了")
//                        .orderId(orderDTO.getOrderId())
//                        .id(transactionId)
//                        .build()
//        );
//    }
//
//    //    处理订单
//    public OrderDTO handleOrder(OrderDTO orderDTO) {
//        log.info("handleOrder");
//        log.info("orderDTO:{}", orderDTO);
//        orderDTO.setOrderEnum(OrderEnum.YES);
//        OrderList orderList = new OrderList();
////        log.info("orderDTO.orderInfo:{}",orderList.getOrderInfo());
//        BeanUtils.copyProperties(orderDTO, orderList, "orderInfo");
//        orderList.setOrderInfo(orderDTO.getOrderInfo().toString());
//        log.info("这是插入到orderList的对象：{}", orderList);
//        this.orderListMapper.insertSelective(orderList);
//        return orderDTO;
//
//    }
//}
