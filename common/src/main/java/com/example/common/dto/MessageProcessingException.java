package com.example.common.dto;

public class MessageProcessingException extends RuntimeException {
  public MessageProcessingException(String message) {
    super(message);
  }
}
