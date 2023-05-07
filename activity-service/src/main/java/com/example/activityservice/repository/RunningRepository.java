package com.example.activityservice.repository;

import com.example.activityservice.dto.CalendarDTO;
import com.example.activityservice.entity.RunningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RunningRepository extends JpaRepository<RunningEntity, Long>, JpaSpecificationExecutor<RunningEntity> {
    List<RunningEntity> findAllByUserId(Long userId);

    List<RunningEntity> findAllByCreatedTimeBetweenAndUserId(Date startDate, Date endDate, Long userId);

    @Query("SELECT rt FROM RunningEntity rt WHERE FUNCTION('DATE_FORMAT', rt.createdTime, '%Y-%m-%d') = FUNCTION('CURDATE')")
    List<RunningEntity> findTodayRunningById(Long userId);

    @Query("SELECT DATE(r.createdTime), ROUND(SUM(r.distance), 1) " +
            "FROM RunningEntity r " +
            "WHERE r.createdTime BETWEEN :startDate AND :endDate AND r.userId = :userId " +
            "GROUP BY DATE(r.createdTime) " +
            "ORDER BY DATE(r.createdTime) ASC")
    List<CalendarDTO.RunningDTO> findRunningByDate(Date startDate, Date endDate, Long userId);


}
