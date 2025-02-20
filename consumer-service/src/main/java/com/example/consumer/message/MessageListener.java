package com.example.consumer.message;

import com.example.common.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageListener {

    @KafkaListener(topics = "messages", groupId = "message-group")
    public void listen(Message message) {
        log.info("Received message: {}", message);
        processMessage(message);
    }

    private void processMessage(Message message) {
        // 비즈니스 로직 구현
        log.info("Processing message with ID: {}", message.getId());
    }
}