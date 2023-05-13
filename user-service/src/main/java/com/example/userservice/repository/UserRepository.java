package com.example.userservice.repository;

import com.example.userservice.domain.CharacterEntity;
import com.example.userservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByUserIdIn(List<Long> friendIds);

}
