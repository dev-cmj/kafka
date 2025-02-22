package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaMessage<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String sender;
    private LocalDateTime timestamp;
    private boolean synchronous;
    private String replyTopic;
    private T payload;
}
