package com.example.activityservice.vo.diary;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDiary {
    private String content;
    private LocalDateTime writeTime;
}
