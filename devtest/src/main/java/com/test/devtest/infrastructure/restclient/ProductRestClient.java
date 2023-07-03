package com.test.devtest.infrastructure.restclient;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.exception.NotFoundException;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.infrastructure.restclient.dto.ProductDetailDTO;
import com.test.devtest.infrastructure.restclient.port.ProductMapperRestClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
@Slf4j
public class ProductRestClient implements ProductClient {

    private static  String SIMILAR_ID = "/similarids";
    static final String PRODUCT_ID_NOT_FOUND = "Product not found";
    static final String INTERNAL_SERVER_API_REST_PRODUCT = "internal server error - layer productRestClient";
    static final String PRODUCT_DETAIL_NOT_FOUND = "Product detail not found";

    private WebClient httpClient;

    private ProductMapperRestClient productMapper;

    public ProductRestClient(WebClient httpClient, ProductMapperRestClient productMapper) {
        this.httpClient = httpClient;
        this.productMapper = productMapper;
    }

    @Override
    public String[] GetSimilarProductsByProductID(String productID) {
        final String uri = productID + SIMILAR_ID;
        return httpClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatusCode.valueOf(404)){
                        throw new NotFoundException(PRODUCT_ID_NOT_FOUND);
                    }
                    log.error("Exception --layer -- productRestClient", clientResponse.statusCode());
                    throw new RuntimeException(INTERNAL_SERVER_API_REST_PRODUCT);
                })
                .bodyToMono(String[].class)
                .block();
    }

    @Override
    public ProductDetail GetProductDetailsByProductID(String productID) {
        ProductDetailDTO productsDetailsDTO =  httpClient.get()
                .uri(productID)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    if (clientResponse.statusCode() == HttpStatusCode.valueOf(404)){
                        throw new NotFoundException(PRODUCT_DETAIL_NOT_FOUND);
                    }
                    log.error("Exception --layer -- productRestClient", clientResponse.statusCode());
                    throw new RuntimeException(INTERNAL_SERVER_API_REST_PRODUCT);
                })
                .bodyToMono(ProductDetailDTO.class)
                .timeout(Duration.ofSeconds(5L))
                .block();

        return productMapper.DTOToDomain(productsDetailsDTO);
    }
}
