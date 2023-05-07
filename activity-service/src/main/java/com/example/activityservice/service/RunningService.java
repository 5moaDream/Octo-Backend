package com.example.activityservice.service;

import com.example.activityservice.entity.RunningEntity;
import com.example.activityservice.repository.RunningRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class RunningService {

    RunningRepository runningRepository;

    public RunningService(RunningRepository runningRepository) {
        this.runningRepository = runningRepository;
    }

    public void createRunning(RunningEntity running) {
        runningRepository.save(running);
    }

    public List<RunningEntity> findTodayRunningById(Long userId) {
        return runningRepository.findTodayRunningById(userId);
    }

    public List<RunningEntity> findWeekRunningById(Long userId) {
        Date currentDate = new Date();
        Date tomorrowDate = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
        Date sevenDaysAgoDate = new Date(currentDate.getTime() - (1000 * 60 * 60 * 24 * 7));

        return runningRepository.findAllByCreatedTimeBetweenAndUserId(sevenDaysAgoDate, tomorrowDate, userId);
    }

    public List<RunningEntity> findAllRunningById(Long userId) {
        List<RunningEntity> runningEntityList = runningRepository.findAllByUserId(userId);
        return runningEntityList;
    }
}
