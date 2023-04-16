package com.example.activityservice.controller;

import com.example.activityservice.dto.diary.RequestDiary;
import com.example.activityservice.dto.diary.ResponseDiary;
import com.example.activityservice.service.DiaryServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    final DiaryServiceImpl service;

    // 다이어리

    /**다이어리 작성*/
    @PostMapping("")
    public ResponseEntity<ResponseDiary> createDiary(@Valid @RequestBody RequestDiary diary){

        ResponseDiary responseDiary = service.createDiary(diary);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDiary);
    }

    /**모든 다이어리 조회*/
    @GetMapping("/{userEmail}")
    public ResponseEntity<List<ResponseDiary>> findAllDiary(@PathVariable String userEmail){
        //페이징 처리 필요
        List<ResponseDiary> ResponseDiaryList = service.findAllDiaryById(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDiaryList);
    }

    /**다이어리 수정*/
    @PutMapping("/{diaryId}")
    public ResponseEntity<ResponseDiary> updateDiary(@Valid @RequestBody RequestDiary diary, @PathVariable long diaryId){
        //save, id 활용
        ResponseDiary responseDiary = service.updateDiary(diary, diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDiary);
    }

    /**다이어리 삭제*/
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Boolean> deleteDiary(@PathVariable long diaryId) {
        Boolean result = service.deleteDiary(diaryId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
