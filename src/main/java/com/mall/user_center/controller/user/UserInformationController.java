package com.mall.user_center.controller.user;

import com.mall.user_center.domain.entity.user_center.UserInformation;
import com.mall.user_center.service.UserInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userInfo")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserInformationController {
    private final UserInformationService userInformationService;

    @GetMapping("/{id}")
    public UserInformation getUserInfoById(@PathVariable Integer id) {
        return this.userInformationService.findById(id);
    }
}
