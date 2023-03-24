package com.example.activityservice.vo.sleep;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponseSleepList {
    private List<ResponseSleep> responseSleepList;
    private long weekAverage;
}
