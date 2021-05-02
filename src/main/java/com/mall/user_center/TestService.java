package com.mall.user_center;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    @SentinelResource("resource")
    public String resource() {
        log.info("resource");
        return "resource";
    }
}
