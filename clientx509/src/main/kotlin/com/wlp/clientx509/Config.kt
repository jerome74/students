package com.wlp.clientx509

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class ConfigClientX509{

    @Bean
    @Throws(Exception::class)
    fun template(): RestTemplate? {
        return RestTemplate()
    }
}