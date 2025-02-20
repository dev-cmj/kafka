package com.example.producer.message;

import com.example.common.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    public void sendMessage(Message message) {
        log.info("Sending message: {}", message);
        kafkaTemplate.send("messages", message.getId(), message);
    }
}