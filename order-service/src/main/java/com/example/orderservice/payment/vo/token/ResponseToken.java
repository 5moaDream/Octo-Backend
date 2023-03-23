package com.example.orderservice.payment.vo.token;

import lombok.Data;

@Data
public class ResponseToken {
    private String access_token;
    private int now;
    private int expired_at;
}
