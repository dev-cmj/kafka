package com.example.consumer.message;

import com.example.common.dto.KafkaMessage;
import com.example.common.dto.MessageHistory;
import com.example.common.dto.MessageHistoryRepository;
import com.example.common.dto.MessageStatus;
import com.example.consumer.message.entity.OrderMessage;
import com.example.consumer.message.entity.UserMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "messages", groupId = "message-group")
    public void listen(String messageJson) {
        try {
            log.info("Received message: {}", messageJson);

            // JSON 문자열을 KafkaMessage 객체로 변환
            KafkaMessage<?> message = objectMapper.readValue(messageJson, KafkaMessage.class);

            // payload를 다시 구체적인 타입으로 변환
            String payloadJson = objectMapper.writeValueAsString(message.getPayload());

            UserMessage userMessage = objectMapper.readValue(payloadJson, UserMessage.class);
            processUserMessage(userMessage);

        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    private void processUserMessage(UserMessage userMessage) {
        log.info("Processing UserMessage: {}", userMessage);
        // 실제 처리 로직
    }
}