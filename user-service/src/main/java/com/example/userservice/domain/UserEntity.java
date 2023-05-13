package com.example.userservice.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USER_TB")
public class UserEntity {
    @Id
    @Column(name = "USER_PK")
    private long userId;    //kakao user id

    @Column(name = "THUMBNAIL_IMAGE_URL")
    private String thumbnailImageUrl;

    @Column(name = "CHARACTER_NM")
    private String characterName;
    @Column(name = "EXPERIENCE_VALUE")
    private int experienceValue;
    @Column(name = "CHARACTER_IMAGE_URL")
    private String characterUrl;

    @Column(name = "STATE_MSG")
    private String stateMsg;

    @Column(name = "TARGET_SLEEP_TIME")
    private int sleepTime;

    @Column(name = "TARGET_RUNNING_DISTANCE")
    private float distance;

    @Column(name = "D_DAY")
    private Date dday;
}
