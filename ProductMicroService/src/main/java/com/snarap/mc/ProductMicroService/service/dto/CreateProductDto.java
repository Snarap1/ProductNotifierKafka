package com.snarap.mc.ProductMicroService.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    private  String title;
    private BigDecimal price;
    private  Integer quantity;
}
