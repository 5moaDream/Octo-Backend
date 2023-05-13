package com.example.userservice.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity(name = "COLLECTION_TB")
public class CollectionEntity {
    @Id
    @Column(name = "COLLECTION_PK")
    Long collectionPK;

    @Column(name = "USER_FK")
    Long userFk;
    @Column(name = "CHARACTER_FK")
    Long characterFk;
}
