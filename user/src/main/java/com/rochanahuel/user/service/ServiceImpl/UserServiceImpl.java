package com.rochanahuel.user.service.ServiceImpl;

import com.rochanahuel.user.dto.UserRequest;
import com.rochanahuel.user.dto.UserResponse;
import com.rochanahuel.user.exceptions.EmailCurrentlyInUse;
import com.rochanahuel.user.mapper.Mappers;
import com.rochanahuel.user.model.User;
import com.rochanahuel.user.repository.UserRepository;
import com.rochanahuel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mappers mappers;

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        Optional<User> emailAvailable = userRepository.findByEmail(userRequest.email());

        if (emailAvailable.isPresent()){
            throw new EmailCurrentlyInUse("The email already has an associated account");
        }

        var newUser = User.builder()
                .firstname(userRequest.firstname())
                .lastname(userRequest.lastname())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();

        userRepository.save(newUser);

        return mappers.userToResponse(newUser);
    }
}
