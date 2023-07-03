package com.test.devtest.application.usecase;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.ProductClient;
import com.test.devtest.domain.port.SimilarProductUseCase;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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
        Arrays.asList(productsID).parallelStream().forEach((productID) ->{
            ProductDetail productDetail= productClient.GetProductDetailsByProductID(productID);
            listProducts.add(productDetail);
                } );

        return listProducts;
    }
}
