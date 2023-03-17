package com.example.activityservice.service;

import com.example.activityservice.vo.diary.RequestDiary;
import com.example.activityservice.vo.diary.ResponseDiary;

import java.util.List;

public interface ActivityService {

    // 다이어리 서비스
    ResponseDiary createDiary(RequestDiary diary);
    ResponseDiary updateDiary(RequestDiary diary, long diaryId);
    boolean deleteDiary(long diaryId);
    List<ResponseDiary> findAllDiaryById(long userId);


    // 러닝 서비스



    // 수면 서비스


    // 캘린더 서비스
}
