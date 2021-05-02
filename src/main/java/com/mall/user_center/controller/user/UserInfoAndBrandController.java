package com.mall.user_center.controller.user;

import com.mall.user_center.auth.CheckLogin;
import com.mall.user_center.domain.dto.user.UserInfoAndBrandDTO;
import com.mall.user_center.service.UserInfoAndBrandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfoAndBrand")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserInfoAndBrandController {
    private final UserInfoAndBrandService userInfoAndBrandService;

//    @GetMapping("/{primaryKey}")
//    @CheckLogin
//    public UserInfoAndBrandDTO getUserInfoAndBrand(@PathVariable Integer primaryKey) {
//        log.info("massage in controller and primarykey is {}", primaryKey);
//        return userInfoAndBrandService.findUserInfoAndBrand(primaryKey);
//    }

}
