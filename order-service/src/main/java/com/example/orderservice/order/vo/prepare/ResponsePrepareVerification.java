package com.example.orderservice.order.vo.prepare;

import lombok.Data;

@Data
public class ResponsePrepareVerification {
    private int code;
    private String message;
    private ResponsePrepare response;
    @Data
    public class ResponsePrepare {
        private String merchant_uid;
        private int amount;
        private String token;
    }
}
