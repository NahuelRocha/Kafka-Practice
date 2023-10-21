package com.rochanahuel.product.service;

import com.rochanahuel.product.dto.ProductRequest;
import com.rochanahuel.product.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);

    String deleteProduct(Long id);
}
