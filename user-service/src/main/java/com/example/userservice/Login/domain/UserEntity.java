package com.example.userservice.Login.domain;

import lombok.*;

import javax.persistence.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="USER_TB")
public class UserEntity {
    @Id
    @Column(name = "USER_PK")
    private long userId;    //kakao user id

    @Column(name = "THUMBNAIL_IMAGE_URL")
    private String thumbnail_image_url;

    @Column(name = "CHARACTER_NM")
    private String characterName;
    @Column(name = "EXPERIENCE_VALUE")
    private int experienceValue;
    @Column(name = "CHARACTER_IMAGE_URL")
    private String character_url;
}
