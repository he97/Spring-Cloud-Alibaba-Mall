package com.mall.user_center.feignClient;

import com.mall.user_center.domain.dto.Brand.BrandDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "commodity-center")
public interface CommodityCenterObjectFeignClient {
    //    目标微服务的url
    @GetMapping("/brandId/objectQuery")
    BrandDTO objectQuery(@SpringQueryMap BrandDTO brandDTO);
}
