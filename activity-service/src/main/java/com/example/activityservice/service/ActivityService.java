package com.example.activityservice.service;

import com.example.activityservice.dto.diary.RequestDiary;
import com.example.activityservice.dto.diary.ResponseDiary;
import com.example.activityservice.dto.guestBook.RequestGuestBook;
import com.example.activityservice.dto.guestBook.ResponseGuestBook;
import com.example.activityservice.dto.running.RequestRunning;
import com.example.activityservice.dto.running.ResponseRunning;
import com.example.activityservice.dto.sleep.RequestSleep;
import com.example.activityservice.dto.sleep.ResponseSleep;
import com.example.activityservice.dto.sleep.ResponseSleepList;

import java.util.List;

public interface ActivityService {

    // 수면 서비스
    ResponseSleep createSleep(RequestSleep sleep);
    ResponseSleepList findAllSleepById(long userId);

    // 러닝 서비스

    ResponseRunning createRunning(RequestRunning running);
    List<ResponseRunning> findTodayRunningById(long userId);
    List<ResponseRunning> findAllRunningById(long userId);











    // 캘린더 서비스
}
