package com.example.activityservice.controller;

import com.example.activityservice.service.ServiceImpl;
import com.example.activityservice.vo.activity.TodayActivity;
import com.example.activityservice.vo.diary.RequestDiary;
import com.example.activityservice.vo.diary.ResponseDiary;
import com.example.activityservice.vo.guestBook.RequestGuestBook;
import com.example.activityservice.vo.guestBook.ResponseGuestBook;
import com.example.activityservice.vo.running.ResponseRunning;
import com.example.activityservice.vo.sleep.RequestSleep;
import com.example.activityservice.vo.sleep.ResponseSleep;
import com.example.activityservice.vo.sleep.ResponseSleepList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    final ServiceImpl service;

    // 다이어리

    /**다이어리 작성*/
    @PostMapping("/diary")
    public ResponseEntity<ResponseDiary> createDiary(@RequestBody RequestDiary diary){

        ResponseDiary responseDiary = service.createDiary(diary);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDiary);
    }

    /**모든 다이어리 조회*/
    @GetMapping("/diary/{userId}")
    public ResponseEntity<List<ResponseDiary>> findAllDiary(@PathVariable long userId){
        //페이징 처리 필요
        List<ResponseDiary> ResponseDiaryList = service.findAllDiaryById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDiaryList);
    }

    /**다이어리 수정*/
    @PutMapping("/diary/{diaryId}")
    public ResponseEntity<ResponseDiary> updateDiary(@RequestBody RequestDiary diary, @PathVariable long diaryId){
        //save, id 활용
        ResponseDiary responseDiary = service.updateDiary(diary, diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDiary);
    }

    /**다이어리 삭제*/
    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<Boolean> deleteDiary(@PathVariable long diaryId) {
        Boolean result = service.deleteDiary(diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    //방명록

    /**방명록 작성*/
    @PostMapping("/guest-book")
    public ResponseEntity<ResponseGuestBook> createGuestBook(@RequestBody RequestGuestBook guestBook){
        ResponseGuestBook responseGuestBook = service.createGuestBook(guestBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseGuestBook);
    }
    /**방명록 전체 조회*/
    @GetMapping("/guest-book/{userId}")
    public ResponseEntity<List<ResponseGuestBook>> findAllGuestBook(@PathVariable long userId){
        List<ResponseGuestBook> responseGuestBooks = service.findAllGuestBookById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBooks);
    }
    /**방명록 업데이트*/
    @PutMapping("/guest-book/{commentId}")
    public ResponseEntity<ResponseGuestBook> modifyGuestBook(@RequestBody RequestGuestBook guestBook, @PathVariable long commentId ){
        ResponseGuestBook responseGuestBook = service.modifyGuestBookById(guestBook,commentId);
        return ResponseEntity.status(HttpStatus.OK).body(responseGuestBook);
    }
    /**방명록 삭제*/
    @DeleteMapping("/guest-book/{commentId}")
    public ResponseEntity<Boolean> deleteGuestBook(@PathVariable long commentId){
        Boolean result = service.deleteGuestBookById(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


    // 수면

    /**수면 시간 기록*/
    @PostMapping("/sleep")
    public ResponseEntity<ResponseSleep> createSleep(@RequestBody RequestSleep sleep){
        //경험치 처리, 당일 수면기록 체크 필요
        ResponseSleep responseSleep = service.createSleep(sleep);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseSleep);
    }

    /**모든 수면 기록 조회*/
    @GetMapping("sleep/{userId}")
    public ResponseEntity<ResponseSleepList> findAllSleep(@PathVariable long userId){
        //페이징
        ResponseSleepList result = service.findAllSleepById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 러닝

    /**러닝 기록*/
    @PostMapping("/running")
    public ResponseEntity<ResponseRunning> createRunning(){
        //경험치 처리
        return null;
    }

    /**당일 총 러닝 시간&거리 조회*/
    @GetMapping("/total-running")
    public ResponseEntity<ResponseRunning> findTodayTotalRunning(){
        return null;
    }

    /**당일 러닝 기록 조회*/
    @GetMapping("/today-running")
    public ResponseEntity<List<ResponseRunning>> findTodayRunning(){
        return null;
    }

    /**모든 러닝 기록 조회*/
    @GetMapping("/running-all")
    public ResponseEntity<ResponseRunning> findAllRunning(){
        return null;
    }


    // 캘린더 조회

    /**모든 기록 조회*/
    @GetMapping("/calander")
    public ResponseEntity<List<TodayActivity>> findCalander(){
        return null;
    }




}
