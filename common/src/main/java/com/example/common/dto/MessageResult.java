package com.example.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResult {
    private String messageId;
    private MessageStatus status;
    private String result;
    private String error;
    private LocalDateTime processedAt;
}