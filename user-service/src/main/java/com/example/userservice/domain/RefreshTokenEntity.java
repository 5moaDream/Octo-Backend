package com.example.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "REFRESH_TOKEN_TB")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @Column(name = "USER_FK")
    private Long userId;   //kakao userId

    @Column(name = "EXPIRATION_TIME")
    private Date expirationTime;
    @Column(name = "REFRESH_TOKEN")
    private String token;
}
