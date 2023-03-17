package com.example.activityservice.DAO;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "RUNNING_TB")
public class RunningDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long runningId;

    private Long userId;
    private LocalDateTime runningStartTime;
    private LocalDateTime runningEndTime;
    private int totalRunningTime;
    private int distance;
}
