package com.mall.user_center.controller.admin;

import com.mall.user_center.auth.CheckAuthorization;
import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import com.mall.user_center.domain.dto.Commodity.CommodityInfoRespDto;
import com.mall.user_center.domain.dto.Commodity.CommodityRespDto;
import com.mall.user_center.domain.dto.adminDto.SearchDto;
import com.mall.user_center.domain.dto.adminDto.UserRespDto;
import com.mall.user_center.domain.dto.order.HandleOrderResp;
import com.mall.user_center.domain.dto.transaction.BoughtCommodityInfoDto;
import com.mall.user_center.domain.dto.transaction.CancelTransactionDto;
import com.mall.user_center.service.AdminService;
import com.mall.user_center.service.HandleOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class adminController {
    private final AdminService adminService;
    private HandleOrderService handleOrderService;

    @GetMapping("requireCheckCommodities")
    @CheckAuthorization("admin")
    public List<CartCommoditiesDTO> getAllRequiredCommodities(){
        return this.adminService.getAllRequiredCommodities();
    }
    @CheckAuthorization("admin")
    @GetMapping("/allUser")
    public List<UserRespDto> getAllUser(){
        return this.adminService.getAllUser();
    }

    @CheckAuthorization("admin")
    @GetMapping("/allTransaction")
    public List<BoughtCommodityInfoDto> getAllTransaction(){
        return this.adminService.getAllTransaction();
    }

    @CheckAuthorization("admin")
    @GetMapping("/allCommodities")
    public List<CommodityInfoRespDto> getAllCommodities(){
        return this.adminService.getAllCommodities();
    }

    @CheckAuthorization("admin")
    @PostMapping("/searchCommodity")
    public List<CommodityRespDto> searchCommodity(@RequestBody SearchDto searchDto){
        return this.adminService.searchCommodity(searchDto);
    }

    @CheckAuthorization("admin")
    @GetMapping("/getInDispute")
    public List<BoughtCommodityInfoDto> getInDispute(){
        return this.adminService.getInDispute();
    }

    @CheckAuthorization("admin")
    @GetMapping("/adminHandleDisputeService")
    public HandleOrderResp handleDisputeService(CancelTransactionDto cancelTransactionDto){
        return this.handleOrderService.adminHandleOrder(cancelTransactionDto);
    }
}
