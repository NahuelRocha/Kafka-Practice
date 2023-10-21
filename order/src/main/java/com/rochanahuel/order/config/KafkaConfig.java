package com.rochanahuel.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic transactionTopic(){

        return TopicBuilder.name("order-created-topic")
                .partitions(2)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic sendForAddStock(){

        return TopicBuilder.name("order-cancel-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
