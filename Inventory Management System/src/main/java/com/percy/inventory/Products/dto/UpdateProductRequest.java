package com.percy.inventory.Products.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {
    private String productName;
    private BigDecimal price;
    private String description;
}
