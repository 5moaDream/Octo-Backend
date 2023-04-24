package com.example.activityservice.repository;

import com.example.activityservice.entity.RunningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RunningRepository extends JpaRepository<RunningEntity, Long>, JpaSpecificationExecutor<RunningEntity> {
}
