package com.example.activityservice.controller;

import com.example.activityservice.entity.DiaryEntity;
import com.example.activityservice.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {
    final DiaryRepository diaryRepository;

    /**다이어리 작성*/
    @PostMapping("")
    public ResponseEntity<DiaryEntity> createDiary(@RequestBody DiaryEntity diary){
        diary.setCreatedTime(new Date());
        DiaryEntity responseDiary = diaryRepository.save(diary);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDiary);
    }

    /**모든 다이어리 조회*/
    @GetMapping("/{userEmail}")
    public ResponseEntity<List<DiaryEntity>> findAllDiary(@PathVariable long userId,
                                                          @PageableDefault(page = 0, size = 10, sort = "diaryId") Pageable pageable){
        //페이징 처리 필요
        List<DiaryEntity> ResponseDiaryList = diaryRepository.findByUserId(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDiaryList);
    }

    /**다이어리 수정*/
    @PutMapping("/{diaryId}")
    public ResponseEntity<DiaryEntity> updateDiary(@RequestBody DiaryEntity diary){
        DiaryEntity responseDiary = diaryRepository.save(diary);
        return ResponseEntity.status(HttpStatus.OK).body(responseDiary);
    }

    /**다이어리 삭제*/
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Boolean> deleteDiary(@PathVariable long diaryId) {
        try{
            diaryRepository.deleteById(diaryId);
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }
}
