package com.example.producer.message;

import com.example.common.dto.KafkaMessage;
import com.example.common.dto.MessageHistoryRepository;
import com.example.common.dto.MessageProcessingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public <T> void sendMessage(T payload, boolean synchronous) {
        try {
            KafkaMessage<T> message = createMessage(payload, synchronous);
            String messageJson = objectMapper.writeValueAsString(message);
            sendToKafka(message.getId(), messageJson);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize message: ", e);
            throw new RuntimeException("Message serialization failed", e);
        }
    }

    private <T> KafkaMessage<T> createMessage(T payload, boolean synchronous) {
        return KafkaMessage.<T>builder()
                .id(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .synchronous(synchronous)
                .payload(payload)
                .build();
    }

    private void sendToKafka(String key, String message) {
        kafkaTemplate.send("messages", key, message)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send message: {}", ex.getMessage());
                    } else {
                        log.info("Message sent successfully: {}", key);
                    }
                });
    }
}