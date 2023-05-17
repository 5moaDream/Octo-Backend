package com.example.activityservice.entity;

import javax.validation.constraints.NotNull;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Table(name = "GUESTBOOK_TB")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUESTBOOK_PK")
    private Long guestBookId;

    @Column(name = "USER_FK")
    @NotNull
    private Long userId;

    @Column(name = "GUEST_FK")
    @NotNull
    private Long guestId;

    @Column(name = "CONTENT")
    @Size(max = 100)
    private String content;

    @Column(name = "READ_FL")
    private boolean read;

    @Column(name = "CREATED_TIME")
    private Timestamp createdTime;
}
