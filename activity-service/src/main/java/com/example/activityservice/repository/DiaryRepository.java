package com.example.activityservice.repository;

import com.example.activityservice.entity.DiaryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long>, JpaSpecificationExecutor<DiaryEntity> {
    List<DiaryEntity> findByUserId(Long userId, Pageable pageable);

}
