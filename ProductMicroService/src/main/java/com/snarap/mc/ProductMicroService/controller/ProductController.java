package com.snarap.mc.ProductMicroService.controller;

import com.snarap.mc.ProductMicroService.service.ProductService;
import com.snarap.mc.ProductMicroService.service.dto.CreateProductDto;
import com.snarap.mc.ProductMicroService.util.ErrorMessage;
import jdk.jshell.Snippet;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/product")

public class ProductController {
    private final ProductService productService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(
            @RequestBody CreateProductDto createProductDto){
        String productId = null;
        try {
            productId = productService.createProduct(createProductDto);
        } catch (Exception e) {
            LOGGER.info(e.getMessage(),e);
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(),e.getMessage()));
        };
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(productId);
    }

}


