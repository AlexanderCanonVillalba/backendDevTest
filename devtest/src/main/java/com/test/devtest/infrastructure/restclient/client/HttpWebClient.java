package com.test.devtest.infrastructure.restclient.client;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Getter
@Configuration
public class HttpWebClient{

    @Value("${existing-apis.base-url}")
    private String BASE_URL;

    @Bean
    public WebClient BuildClient(){
       return WebClient.builder().baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().
                        option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000).doOnConnected(
                                connection ->
                                        connection.addHandlerLast(new ReadTimeoutHandler(3))
                                                .addHandlerLast(new WriteTimeoutHandler(3))
                        )))
                .build();

    }
}
