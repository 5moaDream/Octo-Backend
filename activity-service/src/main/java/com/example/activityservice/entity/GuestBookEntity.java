package com.example.activityservice.entity;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "GUEST_BOOK_TB")
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long commentId;

    @Column(name = "USER_ID")
    @NotNull
    private String userEmail;

    @Column(name = "GUEST_ID")
    private String guestEmail;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "WRITE_TIME")
    private LocalDateTime writeTime;
    @Column(name = "READ")
    private boolean read;
}
