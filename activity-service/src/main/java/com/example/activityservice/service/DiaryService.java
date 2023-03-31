package com.example.activityservice.service;

import com.example.activityservice.dto.diary.RequestDiary;
import com.example.activityservice.dto.diary.ResponseDiary;

import java.util.List;

public interface DiaryService {
    /**다이어리 작성*/
    ResponseDiary createDiary(RequestDiary diary);
    /**모든 다이어리 조회*/
    List<ResponseDiary> findAllDiaryById(String userEmail);
    /**다이어리 수정*/
    ResponseDiary updateDiary(RequestDiary diary, long diaryId);
    /**다이어리 삭제*/
    boolean deleteDiary(long diaryId);
}
