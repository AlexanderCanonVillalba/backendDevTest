package com.test.devtest.infrastructure.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class ProductController {



    @GetMapping("/product/{productId}/similar")
    public String getSimilarProducts(@PathVariable(value="productId") String productId) {
        log.info("Retrieve similar products to product with id: " + productId);
       // List<ProductDetail> similarProducts = retrieveSortedSimilarProductsUseCase.execute(productId);
        return "Hello World!";

    }

}