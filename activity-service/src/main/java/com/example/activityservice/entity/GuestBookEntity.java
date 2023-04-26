package com.example.activityservice.entity;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "GUEST_BOOK_TB")
@NoArgsConstructor
@AllArgsConstructor
public class GuestBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GUSETBOOK_PK")
    private Long guestBookId;

    @Column(name = "USER_FK")
    @NotNull
    private Long userId;
    @Column(name = "GUEST_FK")
    private String guestId;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "READ_FL")
    private boolean isRead;
    @Column(name = "CREATED_TIME")
    private Date createdTime;
}
