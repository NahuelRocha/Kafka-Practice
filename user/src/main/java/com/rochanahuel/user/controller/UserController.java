package com.rochanahuel.user.controller;

import com.rochanahuel.user.dto.UserRequest;
import com.rochanahuel.user.dto.UserResponse;
import com.rochanahuel.user.service.Kafka.KafkaProducerService;
import com.rochanahuel.user.service.ServiceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserServiceImpl userService;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {

        UserResponse newUser = userService.createUser(userRequest);

        UUID uuid = UUID.randomUUID();

        log.info("We received the transaction with the key: " + uuid);

        kafkaProducerService.send("transaction-send-welcome", uuid , newUser);

        return ResponseEntity.ok(newUser);
    }
}
