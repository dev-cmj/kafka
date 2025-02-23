package com.example.common.dto;

public enum MessageStatus {
    // 프로듀서 측 상태
    SENT,           // 메시지 전송 시도됨
    DELIVERED,      // Kafka 브로커에 성공적으로 전달됨
    FAILED,         // 전송 실패

    // 컨슈머 측 상태
    RECEIVED,       // 컨슈머가 메시지를 받음
    PROCESSING,     // 처리 중
    COMPLETED,      // 처리 완료
    ERROR,          // 처리 중 에러 발생

    // 기타 상태
    TIMEOUT,        // 처리 시간 초과
    CANCELLED;      // 취소됨

    public boolean isErrorState() {
        return this == FAILED || this == ERROR || this == TIMEOUT;
    }

    public boolean isFinished() {
        return this == COMPLETED || isErrorState();
    }
}