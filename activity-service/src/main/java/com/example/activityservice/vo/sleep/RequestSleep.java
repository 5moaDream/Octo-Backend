package com.example.activityservice.vo.sleep;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class RequestSleep {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDateTime sleptTime;
    private LocalDateTime wakeUpTime;
    private Long totalSleepTime;
}
