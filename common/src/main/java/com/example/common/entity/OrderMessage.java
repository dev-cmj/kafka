package com.example.common.entity;

import lombok.Data;

@Data
public class OrderMessage {
    private String orderId;
    private String productId;
    private int quantity;
    private String orderStatus;
    private String receiver;
    private String address;
}
