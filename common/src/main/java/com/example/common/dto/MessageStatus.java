package com.example.common.dto;

public enum MessageStatus {
    SENT,           // 메시지 전송됨
    RECEIVED,       // 컨슈머가 수신
    PROCESSING,     // 처리 중
    COMPLETED,      // 처리 완료
    FAILED          // 처리 실패
}
