package com.rochanahuel.inventory.exceptions;

public class ProductNotFoundWithSku extends RuntimeException {

    public ProductNotFoundWithSku(String message) {
        super(message);
    }
}
