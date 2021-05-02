package com.mall.user_center.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "baidu", url = "www.baidu.com")
/**
 * 脱离ribbon使用 name不可少
 */
public interface BaiduFeignClient {
    @GetMapping("")
    String index();
}
