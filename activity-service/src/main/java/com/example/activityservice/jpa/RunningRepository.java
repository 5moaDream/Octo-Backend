package com.example.activityservice.jpa;

import com.example.activityservice.DAO.RunningDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RunningRepository extends JpaRepository<RunningDao, Long>, JpaSpecificationExecutor<RunningDao> {
}
