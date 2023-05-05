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
@RequestMapping("/running")
public class RunningController {
    final RunningService runningService;

    /**러닝 기록*/
    @PostMapping("")
    public HttpStatus createRunning(@RequestBody RunningEntity running){
        runningService.createRunning(running);
        return HttpStatus.CREATED;
    }

    /**당일 러닝 기록 조회*/
    @GetMapping("/today/{userId}")
    public ResponseEntity<List<RunningEntity>> findTodayRunning(@PathVariable Long userId){
        List<RunningEntity> responseRunnings = runningService.findTodayRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }

    /**최근 7일간 러닝 기록 조회*/
    @GetMapping("/week/{userId}")
    public ResponseEntity<List<RunningEntity>> findWeekRunning(@PathVariable Long userId){
        List<RunningEntity> responseRunnings = runningService.findWeekRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }

    /**모든 러닝 기록 조회*/
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<RunningEntity>> findAllRunning(@PathVariable Long userId,
                                                              @PageableDefault(page = 0, size = 30, sort = "runningTime") Pageable pageable){
        List<RunningEntity> responseRunnings = runningService.findAllRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }
}
