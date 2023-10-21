package com.rochanahuel.order.service.Kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    public void send(String topicName, UUID key, Object orderItemsList) {

        var future = kafkaTemplate.send(topicName, key, orderItemsList);

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                LOGGER.error(exception.getMessage());
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }

            LOGGER.info("New transaction: " + orderItemsList);
        });
    }

}
