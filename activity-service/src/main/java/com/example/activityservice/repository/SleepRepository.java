package com.example.activityservice.repository;

import com.example.activityservice.entity.SleepEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SleepRepository extends JpaRepository<SleepEntity, Long>, JpaSpecificationExecutor<SleepEntity> {
    @Query("SELECT st FROM SleepEntity st WHERE FUNCTION('DATE_FORMAT', st.wakeUpTime, '%Y-%m-%d') = FUNCTION('CURDATE')")
    SleepEntity findTodaySleepByUserId(Long userId);

    List<SleepEntity> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT DATE(s.sleptTime), s.totalSleepTime " +
            "FROM SleepEntity s " +
            "WHERE s.sleptTime BETWEEN :startDate AND :endDate AND s.userId = :userId " +
            "ORDER BY DATE(s.sleptTime) ASC")
    List<Object> findSleepByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") Long userId);
}
