package com.rochanahuel.user.service;

import com.rochanahuel.user.dto.UserRequest;
import com.rochanahuel.user.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

}
