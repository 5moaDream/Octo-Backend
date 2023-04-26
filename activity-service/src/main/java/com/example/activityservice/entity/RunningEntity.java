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
    @Column(name = "RUNNING_PK")
    private Long runningId;

    @Column(name = "USER_FK")
    private Long userId;

    @Column(name = "RUNNING_TIME")
    private LocalDateTime runningTime;
    @Column(name = "TOTAL_RUNNING_TIME")
    private long totalRunningTime;
    @Column(name = "DISTANCE")
    private float distance;
}
