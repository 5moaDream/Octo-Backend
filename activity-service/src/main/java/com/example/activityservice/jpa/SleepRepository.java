package com.example.activityservice.jpa;

import com.example.activityservice.DAO.SleepDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SleepRepository extends JpaRepository<SleepDao, Integer> {
}
