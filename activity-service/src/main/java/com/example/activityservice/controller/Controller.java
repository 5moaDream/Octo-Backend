package com.example.activityservice.controller;

import com.example.activityservice.DAO.DiaryDao;
import com.example.activityservice.service.ServiceImpl;
import com.example.activityservice.vo.diary.RequestDiary;
import com.example.activityservice.vo.diary.ResponseDiary;
import com.example.activityservice.vo.running.ResponseRunning;
import com.example.activityservice.vo.sleep.ResponseSleep;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @GetMapping("/diary-all/{userId}")
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

    // 수면

    /**수면 시간 기록*/
    @PostMapping("/sleep")
    public ResponseEntity<ResponseSleep> createSleep(){
        //경험치 처리, 당일 수면기록 체크 필요
        return null;
    }

    /**당일 수면 시간*/
    @GetMapping("/sleep-time")
    public ResponseEntity<Integer> findTodaySleepTime(){
        return null;
    }

    /**당일 수면 기록 조회*/
    @GetMapping("/sleep-today")
    public ResponseEntity<List<ResponseSleep>> findTodaySleep(){
        return null;
    }

    /**모든 수면 기록 조회*/
    @GetMapping("sleep-all")
    public ResponseEntity<List<ResponseSleep>> findAllSleep(){
        //페이징
        return null;
    }

    // 러닝

    /**러닝 기록*/
    @PostMapping("/running")
    public ResponseEntity<ResponseRunning> createRunning(){
        //경험치 처리
        return null;
    }

    /**당일 러닝 시간&거리 조회*/
    @GetMapping("/total-running")
    public ResponseEntity<ResponseRunning> findTodayTotalRunning(){
        return null;
    }

    /**당일 러닝 기록 조회*/
    @GetMapping("/today-running")
    public ResponseEntity<ResponseRunning> findTodayRunning(){
        return null;
    }

    /**모든 러닝 기록 조회*/
    @GetMapping ResponseEntity<ResponseRunning> findAllRunning(){
        return null;
    }


    // 캘린더 조회
}
