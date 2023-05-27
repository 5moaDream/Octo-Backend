package com.example.activityservice.service;

import com.example.activityservice.entity.SleepEntity;
import com.example.activityservice.repository.SleepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SleepService {
    @Autowired
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


    public List<SleepEntity> findWeekSleepById(Long userId) {
        Date currentDate = new Date(System.currentTimeMillis());
        Date tomorrowDate = new Date(currentDate.getTime() + (1000 * 60 * 60 * 24));
        Date sevenDaysAgoDate = new Date(currentDate.getTime() - (1000 * 60 * 60 * 24 * 7));

        return sleepRepository.findAllBySleptTimeBetweenAndUserId(sevenDaysAgoDate, tomorrowDate, userId);
    }

    public SleepEntity findTodaySleepByUserId(Long userId) {
        SleepEntity sleepEntity = sleepRepository.findTodaySleepByUserId(userId);
        return sleepEntity;
    }
}
