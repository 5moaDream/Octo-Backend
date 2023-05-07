package com.example.activityservice.repository;

import com.example.activityservice.dto.CalendarDTO;
import com.example.activityservice.entity.SleepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SleepRepository extends JpaRepository<SleepEntity, Long>, JpaSpecificationExecutor<SleepEntity> {
    @Query("SELECT st FROM SleepEntity st WHERE FUNCTION('DATE_FORMAT', st.wakeUpTime, '%Y-%m-%d') = FUNCTION('CURDATE')")
    List<SleepEntity> findTodaySleepById(Long userId);

    List<SleepEntity> findAllByUserId(Long userId);

    //new Date(yyyy, mm, dd);
    List<SleepEntity> findAllBySleptTimeBetweenAndUserId(Date startDate, Date endDate, Long userId);

    @Query("SELECT DATE(s.sleptTime), s.totalSleepTime " +
            "FROM SleepEntity s " +
            "WHERE s.sleptTime BETWEEN :startDate AND :endDate AND s.userId = :userId " +
            "ORDER BY DATE(s.sleptTime) ASC")
    List<CalendarDTO.SleepDTO> findSleepByDate(Date startDate, Date endDate, Long userId);
}
