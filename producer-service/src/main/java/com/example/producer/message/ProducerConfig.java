package com.example.producer.message;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class ProducerConfig {

    @Bean
    public NewTopic messagesTopic() {
        return TopicBuilder.name("messages")
                .partitions(1)
                .replicas(1)
                .build();
    }
}