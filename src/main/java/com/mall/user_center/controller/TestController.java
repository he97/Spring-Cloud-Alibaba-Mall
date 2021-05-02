package com.mall.user_center.controller;

import com.mall.user_center.domain.dto.Brand.BrandDTO;
import com.mall.user_center.feignClient.CommodityCenterObjectFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableDiscoveryClient
@Slf4j
public class TestController {
    private final DiscoveryClient discoveryClient;

    //    @GetMapping("/client")
//    public List<ServiceInstance> findClient(){
//        List<ServiceInstance> myList = this.discoveryClient.getInstances("commodity-center");
//        System.out.println("myList");
//        for (int i = 0; i < myList.size(); i++) {
//            System.out.println(myList.get(i));
//        }
//        return myList;
//    }
    private final CommodityCenterObjectFeignClient commodityCenterObjectFeignClient;

    @GetMapping("/test")
    public BrandDTO findByObject(BrandDTO brandDTO) {
        log.info("this is 参数:{}", brandDTO);
        return commodityCenterObjectFeignClient.objectQuery(brandDTO);
    }
}
