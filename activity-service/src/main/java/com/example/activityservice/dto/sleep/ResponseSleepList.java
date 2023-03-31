package com.example.activityservice.dto.sleep;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseSleepList {
    private List<ResponseSleep> responseSleepList;
    private long weekAverage;
}
