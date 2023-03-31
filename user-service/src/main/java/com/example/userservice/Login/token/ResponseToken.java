package com.example.userservice.Login.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseToken {
    private String access_token;
    private String refresh_token;
}
