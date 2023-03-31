package com.example.activityservice.dto.running;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRunning {
    private long totalRunningTime;
    private float distance;
    private LocalDate today;
}
