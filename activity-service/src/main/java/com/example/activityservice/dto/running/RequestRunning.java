package com.example.activityservice.dto.running;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRunning {
    @NotNull
    private String userEmail;
    @NotNull
    private LocalDateTime runningStartTime;
    @NotNull
    private LocalDateTime runningEndTime;
    @NotNull
    private float distance;
}
