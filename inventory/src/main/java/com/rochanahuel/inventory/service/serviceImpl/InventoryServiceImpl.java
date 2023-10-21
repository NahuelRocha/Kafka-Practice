package com.rochanahuel.inventory.service.serviceImpl;

import com.rochanahuel.inventory.dto.OrderItemsRequest;
import com.rochanahuel.inventory.dto.ProductSkuToDelete;
import com.rochanahuel.inventory.dto.SaveNewProductInventory;
import com.rochanahuel.inventory.exceptions.ProductNotFoundWithSku;
import com.rochanahuel.inventory.model.Product;
import com.rochanahuel.inventory.repository.InventoryRepository;
import com.rochanahuel.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public void saveNewProductInventory(SaveNewProductInventory product) {

        var saveNewProduct = Product.builder()
                .name(product.getName())
                .sku(product.getSku())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

        inventoryRepository.save(saveNewProduct);
    }

    @Override
    public void updateStockByNewOrderCreated(OrderItemsRequest orderItem) {

        Product getProduct = inventoryRepository.getProductBySku(orderItem.getSku())
                .orElseThrow(() -> new ProductNotFoundWithSku("Product not found with sku: " + orderItem.getSku()));

        getProduct.setStock(getProduct.getStock() - orderItem.getQuantity());

        inventoryRepository.save(getProduct);
    }

    @Override
    public void updateStockByOrderCancel(OrderItemsRequest orderItem) {

        Product getProduct = inventoryRepository.getProductBySku(orderItem.getSku())
                .orElseThrow(() -> new ProductNotFoundWithSku("Product not found with sku: " + orderItem.getSku()));

        getProduct.setStock(getProduct.getStock() + orderItem.getQuantity());

        inventoryRepository.save(getProduct);
    }

    @Override
    public void deleteProductBySku(ProductSkuToDelete sku) {

        Product getProduct = inventoryRepository.getProductBySku(sku.getSku())
                .orElseThrow(() -> new ProductNotFoundWithSku("Product not found with sku: " + sku.getSku()));

       inventoryRepository.delete(getProduct);
    }


}
