package com.test.devtest.application.usecase;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.ProductClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class SimilarProductUseCaseImplTest {

    @Mock
    private ProductClient productClient;
    @InjectMocks
    private SimilarProductUseCaseImpl similarProductUseCase;
    static final String PRODUCT_NAME = "Dress";
    static final double PRICE = 100;


    @Test
    public void TestExecuteWhenGetSimilarProductsByProductIDItsNotFoundShouldReturnListEmptyTest() throws Exception{
      String productID = "test1";
        String[] idsEmpty = new String[0];
        List<ProductDetail> listProductDetail = new ArrayList<>();
        Mockito.when(productClient.GetSimilarProductsByProductID(productID)).thenReturn(idsEmpty);

        List<ProductDetail> productDetailsResult = similarProductUseCase.Execute(productID);

        Assertions.assertEquals(listProductDetail,productDetailsResult );
    }

    @Test
    public void TestExecuteWhenGetProductsDetailsByProductFailsShouldReturnListEmptyTest() throws Exception{
        String productID = "test1";
        String[] idsEmpty = new String[0];
        List<ProductDetail> listProductDetail = new ArrayList<>();
        Mockito.when(productClient.GetSimilarProductsByProductID(productID)).thenReturn(idsEmpty);
        Mockito.when(productClient.GetProductDetailsByProductID(productID)).thenThrow(new RuntimeException("error"));

        List<ProductDetail> productDetailsResult = similarProductUseCase.Execute(productID);

        Assertions.assertEquals(listProductDetail,productDetailsResult );
    }

    @Test
    public void TestExecuteWhenGetProductDetailsIsSuccessfulShouldReturnListTest() throws Exception{
        String productID = "test1";
        String[] productsIds = new String[]{productID};
        ProductDetail productDetail = givenProductDetail(productID);
        List<ProductDetail> listProductDetailExpected = new ArrayList<>();
        Mockito.when(productClient.GetSimilarProductsByProductID(productID)).thenReturn(productsIds);
        Mockito.when(productClient.GetProductDetailsByProductID(productID)).thenReturn(productDetail);

        List<ProductDetail> productDetailsResult = similarProductUseCase.Execute(productID);

        Assertions.assertEquals(productID,productDetailsResult.get(0).getId() );
        Assertions.assertEquals(PRODUCT_NAME,productDetailsResult.get(0).getName());
        Assertions.assertEquals(PRICE,productDetailsResult.get(0).getPrice());
    }

    private ProductDetail givenProductDetail(String productID){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productID);
        productDetail.setName(PRODUCT_NAME);
        productDetail.setPrice(PRICE);
        productDetail.setAvailability(true);
        return productDetail;
    }
}
