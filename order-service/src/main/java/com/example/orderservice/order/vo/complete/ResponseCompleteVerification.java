package com.example.orderservice.order.vo.complete;

import lombok.Data;

@Data
public class ResponseCompleteVerification {
    private int code;
    private String message;
    private ResponseComplete response;
    @Data
    public class ResponseComplete {
        private String merchant_uid;
        private String status;
        private int amount;
    }
}

