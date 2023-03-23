package com.example.orderservice.payment.vo.complete;

import lombok.Data;

@Data
public class ResponseComplete {
    private String merchant_uid;
    private String status;
    private int amount;
}
