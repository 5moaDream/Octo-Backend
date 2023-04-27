package com.example.userservice.token.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestKakaoToken {
    private String access_token;
    private String refresh_token;
}
