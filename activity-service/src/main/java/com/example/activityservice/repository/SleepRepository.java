package com.example.activityservice.repository;

import com.example.activityservice.entity.SleepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SleepRepository extends JpaRepository<SleepEntity, Long>, JpaSpecificationExecutor<SleepEntity> {
    @Query("SELECT st FROM SleepEntity st WHERE FUNCTION('DATE_FORMAT', st.wakeUpTime, '%Y-%m-%d') = FUNCTION('CURDATE')")
    List<SleepEntity> findTodaySleepById(Long userId);

    List<SleepEntity> findAllByUserId(Long userId);
}
