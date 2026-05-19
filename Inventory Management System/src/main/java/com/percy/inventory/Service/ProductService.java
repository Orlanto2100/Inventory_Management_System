package com.percy.inventory.Service;

import com.percy.inventory.Model.Mappers.ProductMapper;
import com.percy.inventory.Model.dto.Request.CreateProductRequest;
import com.percy.inventory.Model.dto.Response.ProductResponse;
import com.percy.inventory.Model.dto.Request.UpdateProductRequest;
import com.percy.inventory.Model.entity.Product;
import com.percy.inventory.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductRequest request) {

        if (productRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("SKU already exists");
        }

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setSku(request.getSku());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toResponse(product);
    }

    public ProductResponse getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.toResponse(product);
    }

    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.getProductName() != null) {
            product.setProductName(request.getProductName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }
}
