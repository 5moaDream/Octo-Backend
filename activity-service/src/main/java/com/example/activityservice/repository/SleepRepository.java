package com.example.activityservice.repository;

import com.example.activityservice.entity.SleepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SleepRepository extends JpaRepository<SleepEntity, Long>, JpaSpecificationExecutor<SleepEntity> {
}
