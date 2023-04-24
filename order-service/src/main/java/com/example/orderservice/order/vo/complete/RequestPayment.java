package com.example.orderservice.order.vo.complete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPayment {
    private String imp_uid;
    private String merchant_uid;
    private Long userId;
    private int itemId;
    private int amount;
    private String token;
}
