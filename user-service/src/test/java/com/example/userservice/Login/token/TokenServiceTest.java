package com.example.userservice.Login.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

class TokenServiceTest {



    @BeforeEach()
    public void afterPropertiesSet() throws Exception {

    }

    @Test
    void 토큰_생성_테스트(){

        //토큰 생성
        String secret = "YzJSbWJtZHNhMnB1YzJSbVp5ZHFNMmx4Y21wbk5ETnZjR3BuYlRRek1EbHBkR2N0YXpRelp6TTFOR2RvZERRPT3jhYHjhLTjhYfjhY7jhYHjhLTjhYfjhLntmLjrgpjjhaPshJzjhZc07J=";
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        long now = (new Date()).getTime();
        System.out.println(new Date(now));
        Date validity = new Date(now + 3600000);   //만료 시간

        String token = Jwts.builder()
                    .setSubject(String.valueOf(1))       //토큰의 주체(주인?) 결정
                    .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                    .setExpiration(validity)                    //만료시간 설정
                    .compact();

        //refresh token
        Duration oneMonth = Duration.ofDays(30);
        Date refreshValidity = Date.from(Instant.now().plus(oneMonth));

        String refreshToken = Jwts.builder()
                .setSubject(String.valueOf(1))       //토큰의 주체(주인?) 결정
                .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                .setExpiration(refreshValidity)                    //만료시간 설정
                .compact();

        System.out.println("access Token: " + token);
        System.out.println("refresh Token: " + refreshToken);
        //토큰 디코딩
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Jws<Claims> jws2 = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNzEzNTgyNDgyIiwiZXhwIjoxNjg1Mzc5MTU4fQ.b7BaDqwgnl_sq13y3ultBS6N8JITZuEELkMwpSLOFYpEylP0Qe-G-a44xkPFkjJN3p3CjvoiiGB9Kr_SkJ2ICQ");

        String userId = jws.getBody().getSubject();
        Date expire = jws.getBody().getExpiration();
        String userId2 = jws2.getBody().getSubject();
        Date expire2 = jws2.getBody().getExpiration();


        System.out.println("access token user ID : " + userId);
        System.out.println("access token 만료 시간: " + expire);
        System.out.println("access token user ID : " + userId2);
        System.out.println("access token 만료 시간: " + expire2);

        System.out.println(new Date().after(expire));   //true = 만료

    }
}