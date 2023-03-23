package com.example.orderservice.payment.vo.complete;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePayment {
    private String status;
    private String message;
}