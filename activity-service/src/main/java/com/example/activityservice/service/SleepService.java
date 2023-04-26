package com.example.activityservice.service;

import com.example.activityservice.entity.SleepEntity;
import com.example.activityservice.repository.SleepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepService {
    SleepRepository sleepRepository;

    public SleepService(SleepRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
    }

    public SleepEntity createSleep(SleepEntity sleep) {
        if(sleep.getTotalSleepTime() < 60)
            return null;

        return sleepRepository.save(sleep);
    }

    public List<SleepEntity> findAllSleepById(Long userId) {
        List<SleepEntity> sleepEntities = sleepRepository.findAllByUserId(userId);
        return sleepEntities;
    }

    public List<SleepEntity> findSleepById(Long userId) {
        List<SleepEntity> sleepEntities = sleepRepository.findTodaySleepById(userId);
        return sleepEntities;
    }
}
