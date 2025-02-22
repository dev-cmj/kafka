package com.example.common.entity;

import lombok.Data;

@Data
public class UserMessage {
    private String userId;
    private String username;
    private String email;
    private String phone;
}
