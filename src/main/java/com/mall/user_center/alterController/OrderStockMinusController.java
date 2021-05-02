package com.mall.user_center.alterController;

import com.mall.user_center.auth.CheckAuthorization;
import com.mall.user_center.domain.dto.alterDTO.OrderDTO;
import com.mall.user_center.domain.entity.user_center.UserInformation;
//import com.mall.user_center.service.OrderStockMinusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderStockMinusController {
//    private final OrderStockMinusService orderStockMinusServiceService;

//    @PutMapping("/add")
//    @CheckAuthorization("admin")
//    public UserInformation alterUserInfoById(@RequestBody OrderDTO userInfoAlterDto) {
//        //TODO 认证，授权
////        return this.orderStockMinusServiceService.auditList(userInfoAlterDto);
//    }

    @PutMapping("/rnm")
    public String testRnm(@RequestBody String string) {
        return string;
    }
}
