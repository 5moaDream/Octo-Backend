package com.example.userservice.Login.repository;

import com.example.userservice.Login.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUserId(long userId);

    @Modifying
    @Query("UPDATE USER_TB u SET u.characterName = ?1 WHERE u.userId = ?2")
    void updateCharacterName(String name, Long id);

    List<UserEntity> findAllByUserIdIn(List<Long> friendIds);

}
