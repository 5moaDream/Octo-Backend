package com.example.activityservice.jpa;

import com.example.activityservice.DAO.DiaryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiaryRepository extends JpaRepository<DiaryDao, Integer>, JpaSpecificationExecutor<DiaryDao> {
}
