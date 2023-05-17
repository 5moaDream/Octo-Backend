package com.example.activityservice.controller;

import com.example.activityservice.entity.RunningEntity;
import com.example.activityservice.service.RunningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RunningController {
    final RunningService runningService;

    /**러닝 기록*/
    @PostMapping("/running")
    public HttpStatus createRunning(@RequestParam("id") Long userId, @RequestBody RunningEntity running){
        running.setUserId(userId);
        runningService.createRunning(running);
        return HttpStatus.CREATED;
    }

    /**당일 러닝 기록 조회*/
    @GetMapping("/running/today")
    public ResponseEntity<List<RunningEntity>> findTodayRunning(@RequestParam("id") Long userId){
        List<RunningEntity> responseRunnings = runningService.findTodayRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }

    /**최근 7일간 러닝 기록 조회*/
    @GetMapping("/running/week")
    public ResponseEntity<List<RunningEntity>> findWeekRunning(@RequestParam("id") Long userId){
        List<RunningEntity> responseRunnings = runningService.findWeekRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }

    /**모든 러닝 기록 조회*/
    @GetMapping("/running/all")
    public ResponseEntity<List<RunningEntity>> findAllRunning(@RequestParam("id") Long userId,
                                                              @PageableDefault(page = 0, size = 50, sort = "createdTime") Pageable pageable){
        List<RunningEntity> responseRunnings = runningService.findAllRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }
}
