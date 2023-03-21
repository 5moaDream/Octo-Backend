package com.example.activityservice.vo.guestBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseGuestBook {
    private Long guestId;
    private String content;
    private LocalDateTime writeTime;
}
