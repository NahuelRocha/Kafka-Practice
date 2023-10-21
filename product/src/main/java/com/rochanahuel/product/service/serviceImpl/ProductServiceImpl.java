package com.rochanahuel.product.service.serviceImpl;

import com.rochanahuel.product.Mappers.Mapper;
import com.rochanahuel.product.dto.ProductRequest;
import com.rochanahuel.product.dto.ProductResponse;
import com.rochanahuel.product.dto.SendNewProductToInventory;
import com.rochanahuel.product.dto.SendProductSkuToDelete;
import com.rochanahuel.product.exceptions.ProductNotFound;
import com.rochanahuel.product.exceptions.SkuInUseException;
import com.rochanahuel.product.model.Product;
import com.rochanahuel.product.repository.ProductRepository;
import com.rochanahuel.product.service.Kafka.KafkaProducerService;
import com.rochanahuel.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final KafkaProducerService kafkaProducerService;
    private final Mapper mapper;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Optional<Product> findProductBySku = productRepository.getProductBySku(productRequest.getSku());

        if (findProductBySku.isPresent()) {
            throw new SkuInUseException("The product SKU already exists in the database.");
        }

        Product newProduct = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .build();

        productRepository.save(newProduct);

        var sendProduct = SendNewProductToInventory.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .sku(productRequest.getSku())
                .stock(productRequest.getStock())
                .build();

        UUID uuid = UUID.randomUUID();

        log.info("We received the transaction with the key: " + uuid);

        kafkaProducerService.send("new-product-topic", uuid, sendProduct);

        return mapper.productToResponse(newProduct);
    }

    @Override
    public String deleteProduct(Long id) {

        var findProductToDelete = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));

        UUID uuid = UUID.randomUUID();

        var sendProductToDeleteInInventory = new SendProductSkuToDelete();
        sendProductToDeleteInInventory.setSku(findProductToDelete.getSku());

        log.info("We receive the request to delete the product with the id: " + uuid);

        kafkaProducerService.send("delete-product-topic", uuid, sendProductToDeleteInInventory);

        productRepository.delete(findProductToDelete);

        return "SUCCESS";
    }
}
