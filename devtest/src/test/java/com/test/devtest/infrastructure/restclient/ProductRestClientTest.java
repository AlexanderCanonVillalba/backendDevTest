package com.test.devtest.infrastructure.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.infrastructure.restclient.dto.ProductDetailDTO;
import com.test.devtest.infrastructure.restclient.port.ProductMapperRestClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductRestClientTest {



    static final String PRODUCT_NAME = "Dress";
    static final double PRICE = 100;

    private final MockWebServer mockWebServer = new MockWebServer();

    @Mock
    private ProductMapperRestClient productMapperRestClient;


    @Test
    void TestGetSimilarProductsByIDWhenIsSuccessfulShouldReturnIds() throws Exception {
        String productID = "test1";
        String[] productsIds = new String[]{productID};
        List<ProductDetail> productDetailList = new ArrayList<>();
        ProductDetail product =  givenProductDetail("productID");
        productDetailList.add(product);
        ProductRestClient productRestClient =
                new ProductRestClient(WebClient.create(mockWebServer.url("/").toString()),productMapperRestClient);
        JSONArray json = new JSONArray(Arrays.asList(productsIds));
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json.toString())
        );

       String[] productIdsResult = productRestClient.GetSimilarProductsByProductID(productID);

       Assertions.assertEquals(productsIds[0],productIdsResult[0]);
    }

    @Test
    void TestGetSimilarProductsByIDWhenFailsShouldReturnError() throws Exception {
        String productID = "test1";
        String[] productsIds = new String[]{productID};
        List<ProductDetail> productDetailList = new ArrayList<>();
        ProductDetail product =  givenProductDetail("productID");
        productDetailList.add(product);
        ProductRestClient productRestClient =
                new ProductRestClient(WebClient.create(mockWebServer.url("/").toString()),productMapperRestClient);
        JSONArray json = new JSONArray(Arrays.asList(productsIds));
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json.toString())
        );
        String errorMessageExpected = "internal server error - layer productRestClient";

        try {
            String[] productIdsResult = productRestClient.GetSimilarProductsByProductID(productID);
            Assertions.assertEquals(new String[0],productIdsResult);
        }catch (Exception e){
            Assertions.assertEquals(errorMessageExpected,e.getMessage());
        }
    }

    @Test
    void TestGetSimilarProductsByIDWhenNotFoundShouldReturnError() throws Exception {
        String productID = "test1";
        String[] productsIds = new String[]{productID};
        ProductRestClient productRestClient =
                new ProductRestClient(WebClient.create(mockWebServer.url("/").toString()),productMapperRestClient);
        JSONArray json = new JSONArray(Arrays.asList(productsIds));
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json.toString())
        );
        String errorMessageExpected = "Product not found";

        try {
            String[] productIdsResult = productRestClient.GetSimilarProductsByProductID(productID);
            Assertions.assertEquals(new String[0],productIdsResult);
        }catch (Exception e){
            Assertions.assertEquals(errorMessageExpected,e.getMessage());
        }
    }

    @Test
    void TestGetProductDetailsByProductIDWhenIsSuccessfulShouldReturnIds() throws Exception {
        String productID = "test1";
        ProductDetailDTO productDetailDTO = givenProductDetailDTO(productID);
        ProductDetail productDomain =  givenProductDetail(productID);
        ProductRestClient productRestClient =
                new ProductRestClient(WebClient.create(mockWebServer.url("/").toString()),productMapperRestClient);
        ObjectMapper mapper = new ObjectMapper();
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(mapper.writeValueAsString(productDetailDTO)
)
        );
        Mockito.when(productMapperRestClient.DTOToDomain(productDetailDTO)).thenReturn(productDomain);

        ProductDetail productResult = productRestClient.GetProductDetailsByProductID(productID);

        Assertions.assertEquals(productDomain,productResult);
    }

    @Test
    void TestGetProductDetailsByProductIDWhenFailsShouldReturnError() throws Exception {
        String productID = "test1";
        ProductDetailDTO productDetailDTO = givenProductDetailDTO(productID);
        ProductRestClient productRestClient =
                new ProductRestClient(WebClient.create(mockWebServer.url("/").toString()),productMapperRestClient);
        ObjectMapper mapper = new ObjectMapper();
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(500)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(mapper.writeValueAsString(productDetailDTO)
                        )
        );
        String errorMessageExpected = "internal server error - layer productRestClient";

        try {
            ProductDetail productResult = productRestClient.GetProductDetailsByProductID(productID);
            Assertions.assertEquals(null,productResult);
        }catch (Exception e){
            Assertions.assertEquals(errorMessageExpected,e.getMessage());
        }
    }

    @Test
    void TestGetProductDetailsByProductIDWhenNotFoundShouldReturnError() throws Exception {
        String productID = "test1";
        ProductDetailDTO productDetailDTO = givenProductDetailDTO(productID);
        ProductRestClient productRestClient =
                new ProductRestClient(WebClient.create(mockWebServer.url("/").toString()),productMapperRestClient);
        ObjectMapper mapper = new ObjectMapper();
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(404)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(mapper.writeValueAsString(productDetailDTO)
                        )
        );
        String errorMessageExpected = "Product detail not found";

        try {
            ProductDetail productResult = productRestClient.GetProductDetailsByProductID(productID);
            Assertions.assertEquals(null,productResult);
        }catch (Exception e){
            Assertions.assertEquals(errorMessageExpected,e.getMessage());
        }
    }



    private ProductDetail givenProductDetail(String productID){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productID);
        productDetail.setName(PRODUCT_NAME);
        productDetail.setPrice(PRICE);
        productDetail.setAvailability(true);
        return productDetail;
    }

    private ProductDetailDTO givenProductDetailDTO(String productID){
        ProductDetailDTO productDetail = new ProductDetailDTO();
        productDetail.setId(productID);
        productDetail.setName(PRODUCT_NAME);
        productDetail.setPrice(PRICE);
        productDetail.setAvailability(true);
        return productDetail;
    }
}