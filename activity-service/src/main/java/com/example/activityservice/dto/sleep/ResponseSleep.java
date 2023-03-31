package com.example.activityservice.dto.sleep;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSleep {
    private LocalDateTime sleptTime;
    private LocalDateTime wakeUpTime;
    private Long totalSleepTime;
}
