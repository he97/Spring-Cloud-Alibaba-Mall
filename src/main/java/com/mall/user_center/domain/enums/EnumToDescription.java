package com.mall.user_center.domain.enums;


public class EnumToDescription {
    public  String getCommodityEnumDescription(String str){
        switch (str){
            case "ON_SALE":
                return "正在售卖";
            case "REJECTED":
                return "没被批准";
            case "HAD_BOUGHT":
                return "已被购买";
            case "UNDER_CARRIAGE":
                return "以下架";
            default:
                throw new IllegalStateException("Unexpected value: " + str);
        }
    }

    public  String getTCMSDesc(String transactionCommodityMessageStatusEnum){
        switch (transactionCommodityMessageStatusEnum){
            case "NOT_HANDLE":
                return "尚未被处理";
            case "HAD_BOUGHT":
                return "已被购买";
            case "FAILED":
                return "购买失败";
            case "WAIT_CANCEL_CONFIRM":
                return "等待是否取消订单";
            case "USER_AGREE_CANCEL":
                return "用户同意取消";
            case "USER_REJECT_CANCEL":
                return "用户反对取消";
            case "ADMIN_AGREE_CONFIRM":
                return "管理员同意取消";
            case "ADMIN_REJECT_CONFIRM":
                return "管理员拒绝取消";
            default:
                throw new IllegalStateException("Unexpected value: " + transactionCommodityMessageStatusEnum);
        }
    }

}
