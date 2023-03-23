package com.example.orderservice.payment.vo.prepare;

import lombok.Data;

@Data
public class ResponsePrepare {
    private String merchant_uid;
    private int amount;
    private String token;
}
