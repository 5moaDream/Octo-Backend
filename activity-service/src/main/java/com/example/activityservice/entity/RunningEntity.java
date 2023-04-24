package com.example.activityservice.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "RUNNING_TB")
@NoArgsConstructor
public class RunningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SLEEP_ID")
    private Long runningId;

    @Column(name = "USER_ID")
    @NotNull
    private String userEmail;

    @Column(name = "RUNNING_START_TIME")
    private LocalDateTime runningStartTime;
    @Column(name = "RUNNING_END_TIME")
    private LocalDateTime runningEndTime;
    @Column(name = "TOTAL_RUNNING_TIME")
    private long totalRunningTime;
    @Column(name = "DISTANCE")
    private float distance;
}
