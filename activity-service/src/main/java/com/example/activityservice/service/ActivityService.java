package com.example.activityservice.service;

import com.example.activityservice.vo.diary.RequestDiary;
import com.example.activityservice.vo.diary.ResponseDiary;
import com.example.activityservice.vo.guestBook.RequestGuestBook;
import com.example.activityservice.vo.guestBook.ResponseGuestBook;
import com.example.activityservice.vo.sleep.RequestSleep;
import com.example.activityservice.vo.sleep.ResponseSleep;
import com.example.activityservice.vo.sleep.ResponseSleepList;

import java.awt.print.Book;
import java.util.List;

public interface ActivityService {

    // 수면 서비스
    ResponseSleep createSleep(RequestSleep sleep);
    ResponseSleepList findAllSleepById(long userId);

    // 다이어리 서비스
    ResponseDiary createDiary(RequestDiary diary);
    List<ResponseDiary> findAllDiaryById(long userId);
    ResponseDiary updateDiary(RequestDiary diary, long diaryId);
    boolean deleteDiary(long diaryId);

    //방명록 서비스
    ResponseGuestBook createGuestBook(RequestGuestBook requestGuestBook);
    List<ResponseGuestBook> findAllGuestBookById(long userId);
    ResponseGuestBook modifyGuestBookById(RequestGuestBook requestGuestBook, long commentId);
    boolean deleteGuestBookById(long commentId);



    // 러닝 서비스





    // 캘린더 서비스
}
