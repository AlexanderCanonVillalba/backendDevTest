package com.test.devtest.domain.port;

import com.test.devtest.domain.entity.ProductDetail;

public interface ProductClient {
    String[] GetSimilarProductsByProductID(String productID);
    ProductDetail GetProductDetailsByProductID(String productID);
}
