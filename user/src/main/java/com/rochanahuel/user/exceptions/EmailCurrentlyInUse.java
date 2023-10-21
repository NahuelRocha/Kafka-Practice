package com.rochanahuel.user.exceptions;

public class EmailCurrentlyInUse extends RuntimeException {

    public EmailCurrentlyInUse(String message) {
        super(message);
    }
}
