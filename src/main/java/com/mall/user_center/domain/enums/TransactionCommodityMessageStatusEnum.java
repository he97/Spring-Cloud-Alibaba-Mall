package com.mall.user_center.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionCommodityMessageStatusEnum {
//  尚未处理
    NOT_HANDLE,
//    已经买了
    HAD_BOUGHT,
//    购买失败
    FAILED,
//    等待是否取消订单
    WAIT_CANCEL_CONFIRM,
//    同意取消
    USER_AGREE_CANCEL,
//    反对取消
    USER_REJECT_CANCEL,
//    管理员同意
    ADMIN_AGREE_CONFIRM,
//    管理员取消
    ADMIN_REJECT_CONFIRM


}
