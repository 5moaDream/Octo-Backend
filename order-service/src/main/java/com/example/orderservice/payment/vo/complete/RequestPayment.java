package com.example.orderservice.payment.vo.complete;

import lombok.Data;

@Data
public class RequestPayment {
    private String imp_uid;
    private String merchant_uid;
    private String token;
    private int userId;
    private int itemId;
    private int amount;
}
