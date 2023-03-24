package com.example.activityservice.vo.sleep;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSleep {
    private LocalDateTime sleptTime;
    private LocalDateTime wakeUpTime;
    private Long totalSleepTime;
}
