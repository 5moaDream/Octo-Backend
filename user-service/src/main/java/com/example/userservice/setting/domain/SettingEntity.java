package com.example.userservice.setting.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name="USER_SETTING_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SETTING_PK")
    private Long settingId;

    @Column(name = "USER_FK")
    private Long userId;

    @Column(name = "SLEEP_TIME")
    private float sleepTime;
    @Column(name = "DISTANCE")
    private float distance;

    @Column(name = "SLEEP_DATE")
    private Date sleepDate;
    @Column(name = "RUNNING_DATE")
    private Date runningDate;
}
