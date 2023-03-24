package com.example.activityservice.jpa;

import com.example.activityservice.DAO.SleepDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SleepRepository extends JpaRepository<SleepDao, Long>, JpaSpecificationExecutor<SleepDao> {
}
