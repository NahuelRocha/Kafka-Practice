package com.rochanahuel.product.Mappers;

import com.rochanahuel.product.dto.ProductResponse;
import com.rochanahuel.product.model.Product;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public ProductResponse productToResponse(Product productRequest) {

        return ProductResponse.builder()
                .id(productRequest.getId())
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();
    }
}
