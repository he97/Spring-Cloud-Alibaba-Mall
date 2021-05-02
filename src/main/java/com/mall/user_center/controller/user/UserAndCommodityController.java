package com.mall.user_center.controller.user;

import com.mall.user_center.auth.CheckLogin;
import com.mall.user_center.domain.dto.Commodity.CartCommoditiesDTO;
import com.mall.user_center.domain.dto.Commodity.HadBoughtInfoDto;
import com.mall.user_center.domain.dto.Commodity.HadBoughtInfoRespDto;
import com.mall.user_center.domain.dto.transaction.BoughtCommodityInfoDto;
import com.mall.user_center.service.UserAndCommodityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userAndCommodities")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserAndCommodityController {
    private final UserAndCommodityService userAndCommodityService;

    @CheckLogin
    @GetMapping("/getUserBought")
    public List<BoughtCommodityInfoDto> getUserBought(){
        return this.userAndCommodityService.getUserBought();
    }
    @CheckLogin
    @PostMapping("/getUserBoughtOrSellInfo")
    public HadBoughtInfoRespDto getUserBoughtOrSellInfo(@RequestBody HadBoughtInfoDto hadBoughtInfoDto){
        return this.userAndCommodityService.getUserBoughtOrSellInfo(hadBoughtInfoDto);
    }
    @CheckLogin
    @GetMapping("/getSell")
    public List<BoughtCommodityInfoDto> getSell(){
        return this.userAndCommodityService.getSell();
    }
}
