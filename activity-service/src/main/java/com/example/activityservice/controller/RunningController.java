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
    public ResponseEntity<RunningEntity> createRunning(@RequestBody RunningEntity running){
        RunningEntity runningEntity = runningService.createRunning(running);
        return ResponseEntity.status(HttpStatus.CREATED).body(runningEntity);

    }

    /**당일 러닝 기록 조회*/
    @GetMapping("/today/{userId}")
    public ResponseEntity<List<RunningEntity>> findTodayTotalRunning(@PathVariable Long userId){
        List<RunningEntity> responseRunnings = runningService.findTodayRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }

    /**모든 러닝 기록 조회*/
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<RunningEntity>> findAllRunning(@PathVariable Long userId,
                                      @PageableDefault(page = 0, size = 10, sort = "runningTime") Pageable pageable){
        List<RunningEntity> responseRunnings = runningService.findAllRunningById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseRunnings);
    }
}
