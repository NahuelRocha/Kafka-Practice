package com.rochanahuel.inventory.repository;

import com.rochanahuel.inventory.model.Product;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Product, Long> {

    Optional<Product> getProductBySku(String sku);
}
