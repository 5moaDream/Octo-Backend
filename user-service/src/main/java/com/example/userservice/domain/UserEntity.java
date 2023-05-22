package com.example.userservice.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @ColumnDefault("나의 문어")
    private String characterName;

    @Column(name = "CHARACTER_IMAGE_URL")
    @ColumnDefault("https://octo-image-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A1%E1%84%80%E1%85%B5%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%A5.png")
    private String characterUrl;

    @Column(name = "EXPERIENCE_VALUE")
    @ColumnDefault("0")
    private Integer experienceValue;

    @Column(name = "STATE_MSG")
    @ColumnDefault("")
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
