package com.example.activityservice.vo.diary;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RequestDiary {
    @NotNull
    private Long userId;
    @NotNull
    private String content;
}
