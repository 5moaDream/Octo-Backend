package com.example.activityservice.vo.running;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRunning {
    private int totalRunningTime;
    private int distance;
}
