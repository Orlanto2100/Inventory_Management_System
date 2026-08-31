package com.percy.inventory.Model.Mappers;

import com.percy.inventory.Model.dto.Response.ProductResponse;
import com.percy.inventory.Model.entity.Product;

public class ProductMapper {
    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getSku(),
                product.getPrice(),
                product.getDescription()
        );
    }
}
