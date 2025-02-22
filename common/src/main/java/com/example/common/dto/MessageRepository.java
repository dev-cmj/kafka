package com.example.common.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    // 상태별 메시지 수 집계
    @Query("SELECT m.status as status, COUNT(m) as count FROM Message m GROUP BY m.status")
    List<MessageStatusCount> countByStatus();

    // 특정 상태의 메시지 조회
    List<Message> findByStatus(MessageStatus status);

    // 특정 기간 내 메시지 조회
    List<Message> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}