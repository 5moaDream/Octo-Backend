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
    private Long userId;    //kakao user id

    @Column(name = "CHARACTER_NM")
    private String characterName;

    @Column(name = "CHARACTER_IMAGE_URL")
    private String characterUrl;

    @Column(name = "EXPERIENCE_VALUE")
    private Integer experienceValue;

    @Column(name = "STATE_MSG")
    private String stateMsg;

    @Column(name = "THUMBNAIL_IMAGE_URL")
    private String thumbnailImageUrl;

    @Column(name = "TARGET_SLEEP_TIME")
    private Integer sleepTime;

    @Column(name = "TARGET_RUNNING_DISTANCE")
    private Float distance;

    @Column(name = "D_DAY")
    private Date dday;
}
