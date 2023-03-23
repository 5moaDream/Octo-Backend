package com.example.orderservice.payment.vo.token;


import lombok.Data;

@Data
public class ResponseTokenData {
    private int code;
    private String message;
    private ResponseToken response;

}
