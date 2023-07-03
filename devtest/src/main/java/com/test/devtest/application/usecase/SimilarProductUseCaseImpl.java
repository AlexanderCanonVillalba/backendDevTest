package com.test.devtest.application.usecase;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.domain.port.SimilarProductUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimilarProductUseCaseImpl implements SimilarProductUseCase {

    private ProductClient productClient;
    public SimilarProductUseCaseImpl(ProductClient productClient) {
        this.productClient = productClient;
    }

    @Override
    public List<ProductDetail> Execute(String productId) {
        List<ProductDetail> listProducts = new ArrayList<>();
        String[] productsID = productClient.GetSimilarProductsByProductID(productId);
        for (String id : productsID) {
            ProductDetail productDetail= productClient.GetProductDetailsByProductID(id);
            listProducts.add(productDetail);
        }
        return listProducts;
    }
}
