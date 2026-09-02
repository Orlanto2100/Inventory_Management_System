package com.percy.inventory.Products;

import com.percy.inventory.Products.dto.CreateProductRequest;
import com.percy.inventory.Products.dto.ProductResponse;
import com.percy.inventory.Products.dto.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody CreateProductRequest request){
        return  productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @GetMapping("/sku/{sku}")
    public ProductResponse getProductBySku(@PathVariable String sku){
        return productService.getProductBySku(sku);
    }

    @GetMapping
    public List<ProductResponse> listProducts(){
        return productService.listProducts();
    }

    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request){
        return productService.updateProduct(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
