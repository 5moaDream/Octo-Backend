package com.example.orderservice.payment.vo;

import lombok.Data;

@Data
public class RequestPayment {
    private int userId;
    private int itemId;
    private int price;
}
