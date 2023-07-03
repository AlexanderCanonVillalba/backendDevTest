package com.test.devtest.infrastructure.controller.port;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.infrastructure.controller.contract.ProductResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

public interface ProductMapper {
    List<ProductResponse> DomainToResponse(List<ProductDetail> lisProductDetail);
}
