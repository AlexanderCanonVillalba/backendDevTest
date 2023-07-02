package com.test.devtest.infrastructure.restclient.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
@Getter
@Configuration
public class HttpClient {

    @Value("${existing-apis.base-url}")
    private String BASE_URL;

    @Value("${existing-apis.similarIds}")
    private String SIMILAR_ID;

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
