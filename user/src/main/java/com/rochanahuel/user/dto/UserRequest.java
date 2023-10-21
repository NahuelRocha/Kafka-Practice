package com.rochanahuel.user.dto;

public record UserRequest(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
