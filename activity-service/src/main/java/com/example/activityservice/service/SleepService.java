package com.example.activityservice.service;

import com.example.activityservice.entity.SleepEntity;
import com.example.activityservice.repository.SleepRepository;
import org.springframework.data.domain.Pageable;
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

    public List<SleepEntity> findAllSleepById(Long userId, Pageable pageable) {
        List<SleepEntity> sleepEntities = sleepRepository.findAllByUserId(userId, pageable);
        return sleepEntities;
    }

    public SleepEntity findTodaySleepByUserId(Long userId) {
        SleepEntity sleepEntity = sleepRepository.findTodaySleepByUserId(userId);
        return sleepEntity;
    }
}
