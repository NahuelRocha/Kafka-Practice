package com.rochanahuel.product.controller;

import com.rochanahuel.product.dto.ProductRequest;
import com.rochanahuel.product.dto.ProductResponse;
import com.rochanahuel.product.service.serviceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @PostMapping("/add")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {

        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {

        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
