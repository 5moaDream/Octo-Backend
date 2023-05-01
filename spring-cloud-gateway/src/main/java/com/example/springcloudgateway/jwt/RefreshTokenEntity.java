package com.example.springcloudgateway.jwt;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "REFRESH_TOKEN_TB")
@Data
public class RefreshTokenEntity {
    @Id
    @Column(name = "USER_FK")
    private long userId;   //kakao userId
    @Column(name = "EXPIRATION_TIME")
    private Date validity;
    @Column(name = "REFRESH_TOKEN")
    private String token;

    public RefreshTokenEntity(long userId, Date expiration_time, String token){
        this.userId = userId;
        this.validity = expiration_time;
        this.token = token;
    }

    public RefreshTokenEntity() {

    }
}
