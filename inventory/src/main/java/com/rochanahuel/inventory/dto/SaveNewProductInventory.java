package com.rochanahuel.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveNewProductInventory {

    private String sku;
    private String name;
    private Double price;
    private Long stock;
}
