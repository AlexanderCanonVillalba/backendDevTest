package com.test.devtest.infrastructure.restclient;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.exception.NotFoundException;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.infrastructure.restclient.client.HttpWebClient;
import com.test.devtest.infrastructure.restclient.dto.ProductDetailDTO;
import com.test.devtest.infrastructure.restclient.port.ProductMapperRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Slf4j
public class ProductRestClient implements ProductClient {

    @Autowired
    private HttpWebClient httpClient;

    @Autowired
    private ProductMapperRestClient productMapper;
    static final String PRODUCT_ID_NOT_FOUND = "Product not found";
    static final String PRODUCT_DETAIL_NOT_FOUND = "Product detail not found";

    @Override
    public String[] GetSimilarProductsByProductID(String productID) {
        final String uri = productID + httpClient.getSIMILAR_ID();
        return httpClient.webClient().get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.error(PRODUCT_ID_NOT_FOUND, clientResponse.statusCode());
                    throw new NotFoundException(PRODUCT_ID_NOT_FOUND);  // throw custom exception
                })
                .bodyToMono(String[].class)
                .timeout(Duration.ofSeconds(5L))
                .block();
    }

    @Override
    public ProductDetail GetProductDetailsByProductID(String productID) {
        ProductDetailDTO productsDetailsDTO =  httpClient.webClient().get()
                .uri(productID)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.error(PRODUCT_DETAIL_NOT_FOUND, clientResponse.statusCode());
                    throw new NotFoundException(PRODUCT_DETAIL_NOT_FOUND);  // throw custom exception
                })
                .bodyToMono(ProductDetailDTO.class)
                .timeout(Duration.ofSeconds(5L))
                .block();

        return productMapper.DTOToDomain(productsDetailsDTO);
    }
}
