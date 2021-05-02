package com.mall.user_center.controller.address;

import com.mall.user_center.auth.CheckLogin;
import com.mall.user_center.domain.dto.address.AddressDto;
import com.mall.user_center.domain.dto.address.GetAddressDto;
import com.mall.user_center.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/address")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserAddressContoller {
    private final AddressService addressService;

    @CheckLogin
    @GetMapping("/selfAddress")
    public List<AddressDto> getUserAddress(){
        return this.addressService.getUserAddress();
    }
    @CheckLogin
    @PostMapping("/getAddress")
    public AddressDto getAddress(@RequestBody GetAddressDto getAddressDto){
        return this.addressService.getAddressById(getAddressDto.getAddressId());
    }
}
