package com.example.activityservice.dto.guestBook;

import lombok.Data;

@Data
public class RequestGuestBook {
    private String userEmail;
    private String guestEmail;
    private String content;
}
