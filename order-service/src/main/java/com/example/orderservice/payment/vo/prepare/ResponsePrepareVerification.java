package com.example.orderservice.payment.vo.prepare;

import lombok.Data;

@Data
public class ResponsePrepareVerification {
    private int code;
    private String message;
    private ResponsePrepare response;
}
