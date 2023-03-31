package com.example.activityservice.service;

import com.example.activityservice.dto.sleep.RequestSleep;
import com.example.activityservice.dto.sleep.ResponseSleep;
import com.example.activityservice.dto.sleep.ResponseSleepList;

public interface SleepService {

    ResponseSleep createSleep(RequestSleep sleep);
    ResponseSleepList findAllSleepById(String userEmail);

}
