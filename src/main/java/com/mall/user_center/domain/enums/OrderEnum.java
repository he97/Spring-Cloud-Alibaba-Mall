package com.mall.user_center.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {
    //    通过
    YES,
    //    不通过
    NO,
    //    尚未审核
    NOT_YET
}
