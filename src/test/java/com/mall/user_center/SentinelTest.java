package com.mall.user_center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

@Slf4j
/**
 * 关联只能关联被监测的url
 */
public class SentinelTest {
    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 1000; i++) {
            String string = restTemplate.getForObject("http://localhost:8070/test-a", String.class);
            Thread.sleep(300);
            log.info("fk:{}", string);
        }
    }
}
