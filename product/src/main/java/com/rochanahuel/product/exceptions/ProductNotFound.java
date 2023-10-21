package com.rochanahuel.product.exceptions;

public class ProductNotFound extends RuntimeException{

    public ProductNotFound(String message){
        super(message);
    }
}
