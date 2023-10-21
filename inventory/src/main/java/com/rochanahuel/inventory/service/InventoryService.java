package com.rochanahuel.inventory.service;

import com.rochanahuel.inventory.dto.OrderItemsRequest;
import com.rochanahuel.inventory.dto.ProductSkuToDelete;
import com.rochanahuel.inventory.dto.SaveNewProductInventory;

import java.util.List;

public interface InventoryService {

    void saveNewProductInventory(SaveNewProductInventory product);

    void updateStockByNewOrderCreated(OrderItemsRequest orderItem);
    void updateStockByOrderCancel(OrderItemsRequest orderItem);

    void deleteProductBySku(ProductSkuToDelete sku);
}
