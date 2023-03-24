package com.example.orderservice.payment.vo.refund;

import lombok.Data;

@Data
public class RequestRefund {
    private long userId;
    private String merchant_uid;
    private int cancel_request_amount;
    private String reason;
}
