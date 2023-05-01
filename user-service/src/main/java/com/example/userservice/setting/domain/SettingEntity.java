package com.example.userservice.setting.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="USER_SETTING_TB")
public class SettingEntity {
    @Id
    @Column(name = "SETTING_PK")
    private Long userId;

    @Column(name = "SLEEP_TIME")
    private float sleepTime;

    @Column(name = "DISTANCE")
    private float distance;

    @Column(name = "D_DAY")
    private Date dday;
}
