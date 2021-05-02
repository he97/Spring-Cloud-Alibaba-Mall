package com.mall.user_center.alterController;

import com.mall.user_center.auth.CheckLogin;
import com.mall.user_center.domain.dto.address.AddressDto;
import com.mall.user_center.domain.dto.address.AddressRespDto;
import com.mall.user_center.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/address")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AlterAddressController {
    private final AddressService addressService;

    @CheckLogin
    @PostMapping("/add")
    public AddressRespDto addUserAddress(@RequestBody AddressDto addressDto){
        return this.addressService.addAddress(addressDto);
    }
    @PostMapping("/alter")
    public AddressRespDto alterAddressById(@RequestBody AddressDto addressDto){
        return this.addressService.alterAddress(addressDto);
    }
}
