package com.rochanahuel.inventory.service.kafka;

import com.rochanahuel.inventory.dto.OrderItemsRequest;
import com.rochanahuel.inventory.dto.ProductSkuToDelete;
import com.rochanahuel.inventory.dto.SaveNewProductInventory;
import com.rochanahuel.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerService {

    private final InventoryService inventoryService;

    @KafkaListener(topics = {"new-product-topic"}, groupId = "new-products-group-id")
    public void newProductConsumer(SaveNewProductInventory product) {

        log.info("We received the product with sku: " + product.getSku()
                + " The quantity is " + product.getStock());

        inventoryService.saveNewProductInventory(product);
    }

    @KafkaListener(topics = {"order-created-topic"}, groupId = "new-orders-group-id", containerFactory = "kafkaListenerOrderItemsFactory")
    public void orderItemsConsumer(OrderItemsRequest order) {

        log.info("Were ordered " + order.getQuantity()
                + " products with the sku: " + order.getSku() + " which will be deducted from stock.");

        inventoryService.updateStockByNewOrderCreated(order);
    }

    @KafkaListener(topics = {"order-cancel-topic"}, groupId = "new-orders-group-id", containerFactory = "kafkaListenerOrderItemsFactory")
    public void orderCancelConsumer(OrderItemsRequest order) {

        log.info("We receive a cancellation of order , we add to the inventory the amount returned for the product with the sku : " + order.getSku()
                + " The quantity is " + order.getQuantity());

        inventoryService.updateStockByOrderCancel(order);
    }

    @KafkaListener(topics = {"delete-product-topic"}, groupId = "delete-product-group-id", containerFactory = "kafkaListenerDeleteProductFactory")
    public void orderCancelConsumer(ProductSkuToDelete product) {

        log.info("We received the request to remove the product with the sku: " + product.getSku());

        inventoryService.deleteProductBySku(product);
    }
}
