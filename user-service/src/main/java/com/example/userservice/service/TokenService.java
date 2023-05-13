package com.example.userservice.service;

import com.example.userservice.domain.RefreshTokenEntity;
import com.example.userservice.dto.TokenSet;
import com.example.userservice.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final long expiration_time = 3600000;
    private final String secret = "YzJSbWJtZHNhMnB1YzJSbVp5ZHFNMmx4Y21wbk5ETnZjR3BuYlRRek1EbHBkR2N0YXpRelp6TTFOR2RvZERRPT3jhYHjhLTjhYfjhY7jhYHjhLTjhYfjhLntmLjrgpjjhaPshJzjhZc07J=";
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    private final RefreshTokenRepository repository;

    //토큰 생성 함수
    public String createToken(Long userId, Date validity){
         return Jwts.builder()
                 .setSubject(String.valueOf(userId))       //토큰의 주체(주인?) 결정
                .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                .setExpiration(validity)                    //만료시간 설정
                .compact();                                 //토큰 생성
    }

    /**서버 토큰 발급*/
    public TokenSet createTokenSet(Long userId){
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration_time);   //만료 시간

        //엑세스 토큰 발급
        String accessToken = createToken(userId, validity);

        //리프레시 토큰 발급
        validity = Date.from(Instant.now().plus(Duration.ofDays(30)));    //기간 한달 설정
        String refreshToken = createToken(userId, validity);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(userId, validity, refreshToken);
        repository.save(refreshTokenEntity);

        return new TokenSet(accessToken, refreshToken);
    }


    /**서버 토큰 재발급*/
    public String renewalToken(String token) throws Exception{
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration_time);

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        Long userId = Long.parseLong(jws.getBody().getSubject());
        RefreshTokenEntity refreshToken = repository.findByUserIdAndExpirationTimeAfter(userId, new Date());

        if(refreshToken.getToken().equals(token))
            return createToken(userId, validity);

        return null;
    }
}