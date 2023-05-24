package com.example.activityservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "SLEEP_TB")
@NoArgsConstructor
public class SleepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLEEP_PK")
    private Long sleepId;

    @Column(name = "USER_FK")
    @NotNull
    private Long userId;

    @Column(name = "SLEPT_TIME")
    private Date sleptTime;

    @Column(name = "WAKEUP_TIME")
    private Date wakeUpTime;

    @Column(name = "TOTAL_SLEEP_TIME")
    private int totalSleepTime;
}
