package com.test.devtest.infrastructure.restclient.port;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.infrastructure.restclient.dto.ProductDetailDTO;

public interface ProductMapperRestClient {
    ProductDetail DTOToDomain(ProductDetailDTO productDetailDTO);
}
