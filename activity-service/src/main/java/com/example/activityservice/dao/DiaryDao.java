package com.example.activityservice.dao;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DIARY_TB")
@NoArgsConstructor
public class DiaryDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_ID")
    private Long diaryId;

    @Column(name = "USER_ID")
    @NotNull
    @Email
    private String userEmail;

    @Column(name = "CONTENT")
    private String content;
    @Column(name = "WRITE_TIME")
    private LocalDateTime writeTime;
}
