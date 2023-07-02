package com.test.devtest.infrastructure.restclient;

import com.test.devtest.domain.entity.ProductDetail;

public interface ProductRestClient {
    String[] GetSimilarProductsByProductID(String productID);
    ProductDetail GetProductDetailsByProductID(String productID);
}
