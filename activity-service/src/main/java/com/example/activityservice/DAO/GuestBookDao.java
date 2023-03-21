package com.example.activityservice.DAO;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "GUEST_BOOK_TB")
public class GuestBookDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentId;

    private Long userId;
    private Long GuestId;
    private String content;
    private LocalDateTime writeTime;
}
