package com.example.activityservice.repository;

import com.example.activityservice.entity.RunningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RunningRepository extends JpaRepository<RunningEntity, Long>, JpaSpecificationExecutor<RunningEntity> {
    List<RunningEntity> findAllByUserId(Long userId);

    @Query("SELECT rt FROM RunningEntity rt WHERE FUNCTION('DATE_FORMAT', rt.runningTime, '%Y-%m-%d') = FUNCTION('CURDATE')")
    List<RunningEntity> findTodayRunningById(Long userId);
}
