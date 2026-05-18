package com.minimarket.compra_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Value("${proveedor-service.url}")
    public String proveedorUrl;

    @Bean
    public WebClient webClient(){
        return WebClient.builder().baseUrl(proveedorUrl).build();
    }
}
