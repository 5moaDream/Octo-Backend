package com.example.activityservice.repository;

import com.example.activityservice.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {


    @Query("SELECT DATE(d.createdTime), d.content " +
            "FROM DiaryEntity d " +
            "WHERE d.createdTime BETWEEN :startDate AND :endDate AND d.userId = :userId " +
            "ORDER BY DATE(d.createdTime) ASC")
    List<Object> findDiaryByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") Long userId);

    List<DiaryEntity> findAllByUserId(Long userId);
}
