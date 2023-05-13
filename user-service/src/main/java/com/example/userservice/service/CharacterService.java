package com.example.userservice.service;

import com.example.userservice.domain.CharacterEntity;
import com.example.userservice.domain.CollectionEntity;
import com.example.userservice.repository.CharacterRepository;
import com.example.userservice.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CharacterService {
    final CharacterRepository characterRepository;
    final CollectionRepository collectionRepository;


    public List<CharacterEntity> findMyCharacter(Long userId) throws Exception{
        List<CollectionEntity> collectionEntityList = collectionRepository.findAllByUserFk(userId);
        List<Long> characterIds = new ArrayList<>();

        for (CollectionEntity c : collectionEntityList) {
            characterIds.add(c.getCharacterFk());
        }

        List<CharacterEntity> characterEntityList = characterRepository.findAllByCharacterIdIn(characterIds);

        return characterEntityList;
    }
}
