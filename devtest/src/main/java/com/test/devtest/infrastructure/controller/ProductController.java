package com.test.devtest.infrastructure.controller;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.SimilarProductUseCase;
import com.test.devtest.infrastructure.controller.contract.ProductResponse;
import com.test.devtest.infrastructure.controller.port.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private SimilarProductUseCase similarProductUseCase;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("/product/{productId}/similar")
    public ResponseEntity<List<ProductResponse>> getSimilarProducts(@PathVariable(value="productId") String productId) {
        log.info("Retrieve similar products to product with id: " + productId);
        if (productId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<ProductDetail> productList = similarProductUseCase.Execute(productId);
            return new ResponseEntity<>(productMapper.DomainToResponse(productList), HttpStatus.OK);
        }catch (Exception e){
            return new HandlerError().ValidateException(e);
        }
    }
}