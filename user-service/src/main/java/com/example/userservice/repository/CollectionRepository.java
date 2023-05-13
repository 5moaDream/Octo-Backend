package com.example.userservice.repository;

import com.example.userservice.domain.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {
    List<CollectionEntity> findAllByUserFk(Long userId);
}
