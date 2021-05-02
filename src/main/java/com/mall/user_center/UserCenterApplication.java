package com.mall.user_center;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.mall.user_center.domain.enums.EnumToDescription;
import com.mall.user_center.rocketmq.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;


//feign全局配置
//@EnableFeignClients(defaultConfiguration = xxx)
@MapperScan("com.mall.user_center.dao")
@EnableFeignClients
@EnableBinding({MySource.class})
@SpringBootApplication
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public EnumToDescription enumToDescription(){
        return new EnumToDescription();

    }
}
