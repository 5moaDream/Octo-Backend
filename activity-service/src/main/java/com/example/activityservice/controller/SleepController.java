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
public class SleepController {
    final SleepService sleepService;

    /**수면 시간 기록*/
    @PostMapping("/sleep")
    public HttpStatus recodeSleep(@RequestParam("id") Long userId, @RequestBody SleepEntity sleep){
        //경험치 처리, 당일 수면기록 체크 필요
        sleep.setUserId(userId);
        SleepEntity responseSleep = sleepService.createSleep(sleep);

        if(responseSleep == null)
            return HttpStatus.BAD_REQUEST;

        return HttpStatus.CREATED;
    }
    /**당일 수면 시간 조회*/
    @GetMapping("/sleep")
    public ResponseEntity<SleepEntity> findTodaySleep(@RequestParam("id") Long userId){
        SleepEntity result = sleepService.findTodaySleepByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**모든 수면 기록 조회*/
    @GetMapping("/sleep/all")
    public ResponseEntity<List<SleepEntity>> findAllSleep(@RequestParam("id") Long userId,
                                      @PageableDefault(page = 0, size = 30, sort = "sleptTime") Pageable pageable){
        //페이징
        List<SleepEntity> result = sleepService.findAllSleepById(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
