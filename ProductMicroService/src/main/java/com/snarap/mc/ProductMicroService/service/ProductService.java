package com.snarap.mc.ProductMicroService.service;

import com.snarap.mc.ProductMicroService.service.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct (CreateProductDto createProductDto) throws ExecutionException, InterruptedException;
}
