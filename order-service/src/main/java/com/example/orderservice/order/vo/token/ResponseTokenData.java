package com.example.orderservice.order.vo.token;


import lombok.Data;

@Data
public class ResponseTokenData {
    private int code;
    private String message;
    private ResponseToken response;
    @Data
    public class ResponseToken {
        private String access_token;
        private int now;
        private int expired_at;
    }
}
