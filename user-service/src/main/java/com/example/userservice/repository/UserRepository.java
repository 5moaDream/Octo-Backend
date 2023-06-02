package com.example.userservice.repository;

import com.example.userservice.domain.CharacterEntity;
import com.example.userservice.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByUserIdIn(List<Long> friendIds);

    @Modifying
    @Query("UPDATE USER_TB u SET u.sleepTime = :sleepTime, u.distance = :distance, u.dday = :dday WHERE u.userId = :userId")
    void updateTarget(Long userId, int sleepTime, float distance, Date dday);

    @Modifying
    @Query("UPDATE USER_TB u SET u.stateMsg = :stateMSG WHERE u.userId = :userId")
    void updateStateMSG(Long userId, String stateMSG);

    @Modifying
    @Query("UPDATE USER_TB u SET u.characterName = :characterName WHERE u.userId = :userId")
    void updateCharacterName(Long userId, String characterName);

    @Modifying
    @Query("UPDATE USER_TB u SET u.characterUrl = :character WHERE u.userId = :userId")
    void updateCharacter(Long userId, String character);
}
