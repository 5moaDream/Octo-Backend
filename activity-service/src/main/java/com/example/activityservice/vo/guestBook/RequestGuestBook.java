package com.example.activityservice.vo.guestBook;

import lombok.Data;

@Data
public class RequestGuestBook {
    private Long userId;
    private Long guestId;
    private String content;
}
