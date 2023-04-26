package com.example.activityservice.controller;

import com.example.activityservice.entity.SleepEntity;
import com.example.activityservice.service.SleepService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sleep")
public class SleepController {
    final SleepService sleepService;

    /**수면 시간 기록*/
    @PostMapping("")
    public ResponseEntity<SleepEntity> createSleep(@RequestBody SleepEntity sleep){
        //경험치 처리, 당일 수면기록 체크 필요
        SleepEntity responseSleep = sleepService.createSleep(sleep);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseSleep);
    }
    /**당일 수면 시간 조회*/
    @GetMapping("/{userId}")
    public ResponseEntity<List<SleepEntity>> findSleep(@PathVariable Long userId){
        List<SleepEntity> result = sleepService.findSleepById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**모든 수면 기록 조회*/
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<SleepEntity>> findAllSleep(@PathVariable Long userId,
                                      @PageableDefault(page = 0, size = 10, sort = "sleptTime") Pageable pageable){
        //페이징
        List<SleepEntity> result = sleepService.findAllSleepById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
