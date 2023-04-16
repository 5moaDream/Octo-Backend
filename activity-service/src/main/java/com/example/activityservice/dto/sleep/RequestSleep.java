package com.example.activityservice.dto.sleep;

import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestSleep {
    @NotNull
    private String userEmail;
    @NotNull
    private LocalDateTime sleptTime;
    private LocalDateTime wakeUpTime;
    private Long totalSleepTime;
}
