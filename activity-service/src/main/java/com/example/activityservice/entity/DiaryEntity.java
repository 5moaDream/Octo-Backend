package com.example.activityservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data   //setter 쓸데없이 쓰지말 것
@Table(name = "DIARY_TB")
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_PK")
    private Long diaryId;

    @Column(name = "USER_FK")
    private Long userId;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "CREATED_TIME")
    private Date createdTime;
}
