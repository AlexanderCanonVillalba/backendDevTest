package com.test.devtest.infrastructure.controller;

import com.test.devtest.application.usecase.SimilarProductUseCaseImpl;
import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.exception.NotFoundException;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.domain.port.SimilarProductUseCase;
import com.test.devtest.infrastructure.controller.contract.ProductResponse;
import com.test.devtest.infrastructure.controller.port.ProductMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerTest {

    @Mock
    private SimilarProductUseCase similarProductUseCase;

    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductController productController;
    static final String PRODUCT_NAME = "Dress";
    static final double PRICE = 100;

    @Test
    public void TestGetSimilarProductsWhenParameterIsInvalidShouldReturnBadRequest() throws Exception{
        String productID = "";

        ResponseEntity productResponse = productController.getSimilarProducts(productID);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST,productResponse.getStatusCode() );
    }

    @Test
    public void TestGetSimilarProductsWhenItsNotFoundShouldReturnNotFound() throws Exception{
        String productID = "test1";
        String[] idsEmpty = new String[0];
        Mockito.when(similarProductUseCase.Execute(productID)).thenThrow(new NotFoundException("error"));

        ResponseEntity productResponse = productController.getSimilarProducts(productID);

        Assertions.assertEquals(HttpStatus.NOT_FOUND,productResponse.getStatusCode() );
    }

    @Test
    public void TestGetSimilarProductsWhenFailsShouldReturnInternalServer() throws Exception{
        String productID = "test1";
        Mockito.when(similarProductUseCase.Execute(productID)).thenThrow(new RuntimeException("Exception message"));

        ResponseEntity productResponse = productController.getSimilarProducts(productID);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,productResponse.getStatusCode() );
    }

    @Test
    public void TestGetSimilarProductsWhenISuccessfulShouldReturnList() throws Exception{
        String productID = "test1";
        List<ProductDetail> listProductsDetails = new ArrayList<>();
        listProductsDetails.add(givenProductDetail(productID));
        List<ProductResponse> productResponseExpected = givenProductResponse(productID);
        Mockito.when(similarProductUseCase.Execute(productID)).thenReturn(listProductsDetails);
        Mockito.when(productMapper.DomainToResponse(listProductsDetails)).thenReturn(productResponseExpected);

        ResponseEntity productResponse = productController.getSimilarProducts(productID);

        Assertions.assertEquals(HttpStatus.OK,productResponse.getStatusCode());
    }

    private ProductDetail givenProductDetail(String productID){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productID);
        productDetail.setName(PRODUCT_NAME);
        productDetail.setPrice(PRICE);
        productDetail.setAvailability(true);
        return productDetail;
    }

    private List<ProductResponse> givenProductResponse(String productID){
        List<ProductResponse> listResponse = new ArrayList<>();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productID);
        productResponse.setName(PRODUCT_NAME);
        productResponse.setPrice(PRICE);
        productResponse.setAvailability(true);
        listResponse.add(productResponse);
        return listResponse;
    }
}