package com.example.activityservice.repository;

import com.example.activityservice.dto.CalendarDTO;
import com.example.activityservice.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {


    @Query("SELECT DATE(d.createdTime), d.content " +
            "FROM DiaryEntity d " +
            "WHERE d.createdTime BETWEEN :startDate AND :endDate AND d.userId = :userId " +
            "ORDER BY DATE(d.createdTime) ASC")
    List<CalendarDTO.DiaryDTO> findDiaryByDate(Date startDate, Date endDate, Long userId);

}
