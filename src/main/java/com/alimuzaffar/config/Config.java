package com.alimuzaffar.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    @Qualifier("loadBalancedRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
