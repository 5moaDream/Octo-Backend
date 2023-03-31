package com.example.activityservice.controller;

import com.example.activityservice.dto.running.RequestRunning;
import com.example.activityservice.dto.running.ResponseRunning;
import com.example.activityservice.service.RunningServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/running")
public class RunningController {
    final RunningServiceImpl service;

    /**러닝 기록*/
    @PostMapping("")
    public ResponseEntity<ResponseRunning> createRunning(@RequestBody RequestRunning running){
        ResponseRunning responseRunning = service.createRunning(running);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseRunning);

    }

    /**당일 러닝 기록 조회*/
    @GetMapping("/today/{userEmail}")
    public ResponseEntity<List<ResponseRunning>> findTodayTotalRunning(@PathVariable String userEmail){
        List<ResponseRunning> responseRunnings = service.findTodayRunningById(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }

    /**모든 러닝 기록 조회*/
    @GetMapping("/all/{userEmail}")
    public ResponseEntity<List<ResponseRunning>> findAllRunning(@PathVariable String userEmail){
        List<ResponseRunning> responseRunnings = service.findAllRunningById(userEmail);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }


}
