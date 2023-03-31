package com.example.activityservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SLEEP_TB")
@NoArgsConstructor
public class SleepDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLEEP_ID")
    private Long sleepId;

    @Column(name = "USER_ID")
    private String userEmail;
    @Column(name = "SLEPT_TIME")
    private LocalDateTime sleptTime;
    @Column(name = "WAKE_UP_TIME")
    private LocalDateTime wakeUpTime;
    @Column(name = "TOTAL_SLEEP_TIME")
    private Long totalSleepTime;     //총 분 = 72 -> 60으로 나눠서 표기
}
