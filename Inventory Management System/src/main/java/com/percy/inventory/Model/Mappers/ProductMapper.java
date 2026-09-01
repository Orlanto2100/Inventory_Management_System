package com.percy.inventory.Model.Mappers;

import com.percy.inventory.Model.dto.Request.CreateProductRequest;
import com.percy.inventory.Model.dto.Request.UpdateProductRequest;
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

    public static Product toEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());

        return product;
    }

    public static void updateEntity(Product product, UpdateProductRequest request) {
        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
    }
}
