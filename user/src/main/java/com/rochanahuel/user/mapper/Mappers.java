package com.rochanahuel.user.mapper;

import com.rochanahuel.user.dto.UserResponse;
import com.rochanahuel.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mappers {

    public UserResponse userToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
    }
}
