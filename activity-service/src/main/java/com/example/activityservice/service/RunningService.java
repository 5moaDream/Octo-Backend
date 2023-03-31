package com.example.activityservice.service;

import com.example.activityservice.dto.running.RequestRunning;
import com.example.activityservice.dto.running.ResponseRunning;

import java.util.List;

public interface RunningService {
    ResponseRunning createRunning(RequestRunning running);
    List<ResponseRunning> findTodayRunningById(String userEmail);
    List<ResponseRunning> findAllRunningById(String userEmail);


}
