package com.test.devtest;


import com.test.devtest.infrastructure.controller.mapper.ProductMapperImpl;
import com.test.devtest.infrastructure.controller.port.ProductMapper;
import com.test.devtest.infrastructure.restclient.mapper.ProductMapperImpRestClient;
import com.test.devtest.infrastructure.restclient.port.ProductMapperRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ProductMapper productMapperImpl(){
        return new ProductMapperImpl();
    }

    @Bean
    public ProductMapperRestClient productMapperRestClient(){
        return new ProductMapperImpRestClient();
    }
}
