package com.test.devtest.application.usecase;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.domain.port.SimilarProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Configuration
public class SimilarProductUseCaseImpl implements SimilarProductUseCase {

    private ProductClient productClient;
    public SimilarProductUseCaseImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<ProductDetail> Execute(String productId) {
        List<ProductDetail> listProducts = new ArrayList<>();
        String[] productsID = this.productClient.GetSimilarProductsByProductID(productId);
        for (String id : productsID) {
            ProductDetail productDetail= productClient.GetProductDetailsByProductID(id);
            listProducts.add(productDetail);
        }
        return listProducts;
    }
}
