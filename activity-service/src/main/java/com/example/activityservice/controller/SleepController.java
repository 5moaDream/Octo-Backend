package com.example.activityservice.controller;

import com.example.activityservice.dto.sleep.RequestSleep;
import com.example.activityservice.dto.sleep.ResponseSleep;
import com.example.activityservice.dto.sleep.ResponseSleepList;
import com.example.activityservice.service.SleepServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sleep")
public class SleepController {
    final SleepServiceImpl service;

    /**수면 시간 기록*/
    @PostMapping("")
    public ResponseEntity<ResponseSleep> createSleep(@RequestBody RequestSleep sleep){
        //경험치 처리, 당일 수면기록 체크 필요
        ResponseSleep responseSleep = service.createSleep(sleep);

        if(responseSleep == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseSleep);
    }

    /**모든 수면 기록 조회*/
    @GetMapping("/{userEmail}")
    public ResponseEntity<ResponseSleepList> findAllSleep(@PathVariable String userEmail){
        //페이징
        ResponseSleepList result = service.findAllSleepById(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
