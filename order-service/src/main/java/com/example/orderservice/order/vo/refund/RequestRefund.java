package com.example.orderservice.order.vo.refund;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestRefund {
    private String merchant_uid;
    private int cancel_request_amount;
    private String reason;
}
