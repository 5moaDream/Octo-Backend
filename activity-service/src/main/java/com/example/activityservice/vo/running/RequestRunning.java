package com.example.activityservice.vo.running;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRunning {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDateTime runningStartTime;
    @NotNull
    private LocalDateTime runningEndTime;
}
