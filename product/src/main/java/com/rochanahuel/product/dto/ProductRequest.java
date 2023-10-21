package com.rochanahuel.product.dto;

import lombok.Data;

@Data
public class ProductRequest {

    private String sku;
    private String name;
    private String description;
    private Double price;
    private Long stock;
}
