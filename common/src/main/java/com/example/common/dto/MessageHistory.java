package com.example.common.dto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageHistory {
    @Id
    private String messageId;

    private String sender;
    private LocalDateTime timestamp;
    private String payloadType;  // 어떤 타입의 메시지였는지

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    // 메시지 내용을 JSON으로 저장
    @Column(columnDefinition = "TEXT")
    private String payloadJson;

    private LocalDateTime processedAt;
    private String errorMessage;
}