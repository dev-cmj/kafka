package com.example.common.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private String id;

    private String content;
    private String sender;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    private LocalDateTime timestamp;

    private String result;
    private String error;
    private LocalDateTime processedAt;
}