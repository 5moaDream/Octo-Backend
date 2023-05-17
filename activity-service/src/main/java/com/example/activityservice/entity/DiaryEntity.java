package com.example.activityservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Builder
@Entity
@Data
@Table(name = "DIARY_TB")
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIARY_PK")
    private Long diaryId;

    @Column(name = "USER_FK")
    @NotNull
    private Long userId;

    @Column(name = "CONTENT")
    @Length(max = 100)
    private String content;

    @Column(name = "CREATED_TIME")
    private Date createdTime;
}
