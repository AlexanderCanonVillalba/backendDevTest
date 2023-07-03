package com.test.devtest.infrastructure.restclient.mapper;


import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.infrastructure.restclient.dto.ProductDetailDTO;
import com.test.devtest.infrastructure.restclient.port.ProductMapperRestClient;

public class ProductMapperImpRestClient implements ProductMapperRestClient {

    @Override
    public ProductDetail DTOToDomain(ProductDetailDTO productDetailDTO){
        ProductDetail productDetail = new ProductDetail();
        productDetail.setId(productDetailDTO.getId());
        productDetail.setName(productDetailDTO.getName());
        productDetail.setPrice(productDetailDTO.getPrice());
        productDetail.setAvailability(productDetailDTO.isAvailability());
        return productDetail;
    }

}
