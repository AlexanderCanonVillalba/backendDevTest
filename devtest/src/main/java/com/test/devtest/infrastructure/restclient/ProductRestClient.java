package com.test.devtest.infrastructure.restclient;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.infrastructure.restclient.client.HttpClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@Slf4j
public class ProductRestClient implements ProductClient {

    @Autowired
    private HttpClient httpClient;

    @Override
    public String[] GetSimilarProductsByProductID(String productID) {
        final String uri = httpClient.getBASE_URL() + productID + httpClient.getSIMILAR_ID();
        return httpClient.webClient().get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrors)
                .bodyToMono(String[].class)
                .timeout(Duration.ofSeconds(5L))
                .block();
    }

    @Override
    public ProductDetail GetProductDetailsByProductID(String productID) {

        final String uri = httpClient.getBASE_URL() + productID;

        return httpClient.webClient().get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleErrors)
                .bodyToMono(ProductDetail.class)
                .timeout(Duration.ofSeconds(5L))
                .block();
    }

    private Mono<Throwable> handleErrors(ClientResponse response ){
        return response.bodyToMono(String.class).flatMap(body -> {
            log.error("Product not found");
            return Mono.error(new Exception());
        });
    }
}
