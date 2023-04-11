package com.example.orderservice.payment.vo.refund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestRefund {
    private String userEmail;
    private String merchant_uid;
    private int cancel_request_amount;
    private String reason;
}
