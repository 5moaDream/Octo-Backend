package com.example.activityservice.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    @NotNull
    private Long userId;

    @Column(name = "CREATED_TIME")
    private Date createdTime;

    @Column(name = "TOTAL_RUNNING_TIME")
    private int totalRunningTime;

    @Column(name = "DISTANCE")
    private float distance;
}
