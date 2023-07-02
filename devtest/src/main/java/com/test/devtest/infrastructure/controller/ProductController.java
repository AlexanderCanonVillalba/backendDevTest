package com.test.devtest.infrastructure.controller;

import com.test.devtest.domain.entity.ProductDetail;
import com.test.devtest.domain.port.SimilarProductUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@Slf4j
public class ProductController {

    @Autowired
    private SimilarProductUseCase similarProductUseCase;

    @GetMapping("/product/{productId}/similar")
    public ResponseEntity<List<ProductDetail>> getSimilarProducts(@PathVariable(value="productId") String productId) {
        log.info("Retrieve similar products to product with id: " + productId);
        if (productId.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<ProductDetail> productList = similarProductUseCase.Execute(productId);
       // List<ProductDetail> similarProducts = retrieveSortedSimilarProductsUseCase.execute(productId);
         return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}