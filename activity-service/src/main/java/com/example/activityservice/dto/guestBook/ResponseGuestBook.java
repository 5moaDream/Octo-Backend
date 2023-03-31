package com.example.activityservice.dto.guestBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGuestBook {
    private String guestEmail;
    private String content;
    private LocalDateTime writeTime;
    private boolean read;
}
