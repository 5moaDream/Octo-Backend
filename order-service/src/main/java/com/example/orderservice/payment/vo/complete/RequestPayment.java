package com.example.orderservice.payment.vo.complete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayment {
    private String imp_uid;
    private String merchant_uid;
    private String token;
    private String userEmail;
    private int itemId;
    private int amount;
}
