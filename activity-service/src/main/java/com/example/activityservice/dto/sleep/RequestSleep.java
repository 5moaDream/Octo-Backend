package com.example.activityservice.dto.sleep;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestSleep {
    @NotNull
    private String userId;
    @NotNull
    private LocalDateTime sleptTime;
    private LocalDateTime wakeUpTime;
    private Long totalSleepTime;
}
