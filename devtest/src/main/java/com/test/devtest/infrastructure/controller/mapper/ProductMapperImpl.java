package com.test.devtest.infrastructure.controller.mapper;


import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.infrastructure.controller.contract.ProductResponse;
import com.test.devtest.infrastructure.controller.port.ProductMapper;

import java.util.ArrayList;
import java.util.List;

public class ProductMapperImpl implements ProductMapper {
    @Override
     public List<ProductResponse> DomainToResponse(List<ProductDetail> lisProductDetail){
         List<ProductResponse> lisProductResponse = new ArrayList<>();
         for (ProductDetail productDetail:lisProductDetail){
             ProductResponse productResponse = new ProductResponse();
             productResponse.setId(productDetail.getId());
             productResponse.setName(productDetail.getName());
             productResponse.setPrice(productDetail.getPrice());
             productResponse.setAvailability(productDetail.isAvailability());
             lisProductResponse.add(productResponse);
         }
         return  lisProductResponse;
     }

}
