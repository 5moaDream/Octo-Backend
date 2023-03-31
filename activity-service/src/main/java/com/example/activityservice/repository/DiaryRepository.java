package com.example.activityservice.repository;

import com.example.activityservice.dao.DiaryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DiaryRepository extends JpaRepository<DiaryDao, Long>, JpaSpecificationExecutor<DiaryDao> {
}
