package com.rochanahuel.user.service.Kafka;

import com.rochanahuel.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<UUID, UserResponse> kafkaTemplate;

    public void send(String topicName, UUID key, UserResponse transactionMessage) {

        var future = kafkaTemplate.send(topicName, key, transactionMessage);

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                LOGGER.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }

            LOGGER.info("New user registered on the platform, his id is: " + transactionMessage.getId() +
                    " The email is: " + transactionMessage.getEmail());
        });
    }

}
