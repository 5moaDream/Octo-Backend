package com.example.userservice.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity(name = "CHARACTER_TB")
public class CharacterEntity {
    @Id
    @Column(name = "CHARACTER_PK")
    long characterId;

    @Column(name = "CHARACTER_NM")
    String characterName;

    @Column(name = "CHARACTER_IMAGE_URL")
    String characterImageUrl;
}
