package com.mall.user_center.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;


public class CommodityCenterGeignConfiguration {
    @Bean
    public Logger.Level level() {
//        让日志打印级别变为Full
//        NONE,
//        BASIC,
//        HEADERS,
//        FULL;
        return Logger.Level.BASIC;
    }
}
