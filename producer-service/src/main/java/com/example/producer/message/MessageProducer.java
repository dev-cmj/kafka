package com.example.producer.message;

import com.example.common.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final MessageRepository messageRepository;  // 메시지 저장소

    @Transactional
    public Message sendMessage(Message message) {
        try {
            message.setId(UUID.randomUUID().toString());
            message.setTimestamp(LocalDateTime.now());
            message.setStatus(MessageStatus.SENT);

            // DB에 메시지 저장
            messageRepository.save(message);

            // Kafka로 메시지 전송
            kafkaTemplate.send("messages", message.getId(), message);


            return message;
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
            throw new MessageProcessingException("Failed to process message");
        }
    }

    // 결과 처리를 위한 리스너
    @KafkaListener(topics = "message-results", groupId = "producer-group")
    public void handleResult(MessageResult result) {
        try {
            Message message = messageRepository.findById(result.getMessageId())
                    .orElseThrow(() -> new MessageNotFoundException(result.getMessageId()));

            message.setStatus(result.getStatus());
            messageRepository.save(message);

            if (result.getStatus() == MessageStatus.FAILED) {
                log.error("Message processing failed: {}", result.getError());
                // 재처리 로직 구현 가능
            }
        } catch (Exception e) {
            log.error("Error handling result: {}", e.getMessage());
        }
    }
}