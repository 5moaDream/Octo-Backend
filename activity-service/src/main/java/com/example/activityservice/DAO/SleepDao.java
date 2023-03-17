package com.example.activityservice.DAO;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "SLEEP_TB")
public class SleepDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sleepId;

    private Long userId;
    private LocalDateTime sleptTime;
    private LocalDateTime wakeUpTime;
    private int totalSleepTime;     //총 분 = 72 -> 60으로 나눠서 표기
}
