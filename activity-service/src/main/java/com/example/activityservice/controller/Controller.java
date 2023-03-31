package com.example.activityservice.controller;

import com.example.activityservice.service.ServiceImpl;
import com.example.activityservice.dto.activity.TodayActivity;
import com.example.activityservice.dto.running.RequestRunning;
import com.example.activityservice.dto.running.ResponseRunning;
import com.example.activityservice.dto.sleep.RequestSleep;
import com.example.activityservice.dto.sleep.ResponseSleep;
import com.example.activityservice.dto.sleep.ResponseSleepList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Controller {

    final ServiceImpl service;

    // 수면

    /**수면 시간 기록*/
    @PostMapping("/sleep")
    public ResponseEntity<ResponseSleep> createSleep(@RequestBody RequestSleep sleep){
        //경험치 처리, 당일 수면기록 체크 필요
        ResponseSleep responseSleep = service.createSleep(sleep);

        if(responseSleep == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

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


    // 캘린더 조회

    /**모든 기록 조회*/
    @GetMapping("/calander")
    public ResponseEntity<List<TodayActivity>> findCalander(){
        return null;
    }


    // 메인 화면 데이터 조회

}
