package com.test.devtest.domain.port;

import com.test.devtest.domain.entity.ProductDetail;

import java.util.List;

public interface SimilarProductUseCase {
    List<ProductDetail> Execute(String productId);
}
