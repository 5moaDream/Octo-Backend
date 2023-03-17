package com.example.activityservice.DAO;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DIARY_TB")
public class DiaryDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;

    private Long userId;
    private String content;
    private LocalDateTime writeTime;
}
