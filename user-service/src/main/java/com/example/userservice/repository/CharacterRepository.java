package com.example.userservice.repository;

import com.example.userservice.domain.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

    List<CharacterEntity> findAllByCharacterIdIn(List<Long> characterId);
}
