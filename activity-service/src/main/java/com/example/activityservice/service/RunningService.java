package com.example.activityservice.service;

import com.example.activityservice.entity.RunningEntity;
import com.example.activityservice.repository.RunningRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RunningService {

    RunningRepository runningRepository;

    public RunningService(RunningRepository runningRepository) {
        this.runningRepository = runningRepository;
    }

    public RunningEntity createRunning(RunningEntity running) {
        return runningRepository.save(running);
    }

    public List<RunningEntity> findTodayRunningById(Long userId) {
        return runningRepository.findTodayRunningById(userId);
    }

    public List<RunningEntity> findAllRunningById(Long userId) {
        List<RunningEntity> runningEntityList = runningRepository.findAllByUserId(userId);
        return runningEntityList;
    }
}
