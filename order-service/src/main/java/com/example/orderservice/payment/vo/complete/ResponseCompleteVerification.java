package com.example.orderservice.payment.vo.complete;

import lombok.Data;

@Data
public class ResponseCompleteVerification {
    private int code;
    private String message;
    private ResponseComplete response;
}

