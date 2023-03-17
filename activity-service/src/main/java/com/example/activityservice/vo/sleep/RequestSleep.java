package com.example.activityservice.vo.sleep;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestSleep {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDateTime sleptTime;
    @NotNull
    private LocalDateTime wakeUpTime;
}
