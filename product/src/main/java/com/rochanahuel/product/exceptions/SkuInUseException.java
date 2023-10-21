package com.rochanahuel.product.exceptions;

public class SkuInUseException extends RuntimeException {

    public SkuInUseException(String message) {
        super(message);
    }
}
