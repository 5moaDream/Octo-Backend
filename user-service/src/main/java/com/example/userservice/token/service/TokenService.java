package com.example.userservice.token.service;

import com.example.userservice.token.domain.RefreshTokenEntity;
import com.example.userservice.token.dto.KakaoTokenDTO;
import com.example.userservice.token.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final WebClient webClient = WebClient.create();
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String CLIENT_ID = "09201921cd5e210ee03ddd019e256454";

    private static final String REDIRECT_URI = "http://127.0.0.1:8000/unauthorization/code";
    private static final String GRANT_TYPE = "authorization_code";


    private final long expiration_time = 3600000;
    private final String secret = "YzJSbWJtZHNhMnB1YzJSbVp5ZHFNMmx4Y21wbk5ETnZjR3BuYlRRek1EbHBkR2N0YXpRelp6TTFOR2RvZERRPT3jhYHjhLTjhYfjhY7jhYHjhLTjhYfjhLntmLjrgpjjhaPshJzjhZc07J=";
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    private final RefreshTokenRepository repository;



    public KakaoTokenDTO getToken(String code) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;

        Flux<KakaoTokenDTO> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenDTO.class);

        return response.blockFirst();
    }

    public String reissuanceToken(String token){
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                                    .setSigningKey(key)
                                    .build()
                                    .parseClaimsJws(token);

            Long userId = Long.parseLong(jws.getBody().getSubject());
            RefreshTokenEntity refreshToken = repository.findByUserIdAndExpirationTimeAfter(userId, new Date());

            if(refreshToken.getToken().equals(token))
                return createToken(userId);

        } catch (Exception ex) {
            //토큰 만료
        }
        return null;
    }
    public String reissuanceKakaoToken(String refreshToken) throws HttpClientErrorException.Unauthorized {
        String uri = TOKEN_URI + "?grant_type=refresh_token&client_id=" + CLIENT_ID + "&refresh_token=" + refreshToken;
        Flux<KakaoTokenDTO> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenDTO.class);

        return response.blockFirst().getAccess_token();
    }

    public String createToken(Long userId){
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration_time);   //만료 시간

        return Jwts.builder()
                .setSubject(String.valueOf(userId))       //토큰의 주체(주인?) 결정
                .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                .setExpiration(validity)                    //만료시간 설정
                .compact();                                 //토큰 생성
    }

    public String createRefreshToken(Long userId){
        Duration oneMonth = Duration.ofDays(30);
        Date validity = Date.from(Instant.now().plus(oneMonth));    //기간 한달 설정

        String token = Jwts.builder()
                        .setSubject(String.valueOf(userId))       //토큰의 주체(주인?) 결정
                        .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                        .setExpiration(validity)                    //만료시간 설정
                        .compact();                                 //토큰 생성

        RefreshTokenEntity refreshToken = new RefreshTokenEntity(userId, validity, token);
        repository.save(refreshToken);
        return token;
    }
}