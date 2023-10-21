package com.rochanahuel.notification.service.kafka;

import com.rochanahuel.notification.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaConsumerService {

    @KafkaListener(topics = {"transaction-send-welcome"}, groupId = "new-user-welcome-group-id")
    public void newProductConsumer(UserResponse newUser) {

        log.info("A welcome email was sent to: " + newUser.getFirstname() + " " + newUser.getLastname()
                + " your email is: " + newUser.getEmail());
    }

}
