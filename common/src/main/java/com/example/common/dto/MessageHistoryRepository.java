package com.example.common.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface MessageHistoryRepository extends JpaRepository<MessageHistory, String> {
    // 실패한 메시지 조회
    List<MessageHistory> findByStatus(MessageStatus status);

    // 특정 시간 동안 처리되지 않은 메시지 조회
    List<MessageHistory> findByStatusNotAndTimestampBefore(
            MessageStatus status,
            LocalDateTime timestamp
    );
}
