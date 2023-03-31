package com.example.activityservice.repository;

import com.example.activityservice.dao.SleepDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SleepRepository extends JpaRepository<SleepDao, Long>, JpaSpecificationExecutor<SleepDao> {
}
