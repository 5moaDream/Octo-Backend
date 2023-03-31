package com.example.activityservice.repository;

import com.example.activityservice.dao.RunningDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RunningRepository extends JpaRepository<RunningDao, Long>, JpaSpecificationExecutor<RunningDao> {
}
