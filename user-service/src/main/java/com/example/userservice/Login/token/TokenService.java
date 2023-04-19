package com.example.userservice.Login.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenService implements InitializingBean {
    private final WebClient webClient = WebClient.create();
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String CLIENT_ID = "86aba9aec0985787d071adc7c329635d";

    private final long expiration_time = 3600000;
    private final String secret = "YzJSbWJtZHNhMnB1YzJSbVp5ZHFNMmx4Y21wbk5ETnZjR3BuYlRRek1EbHBkR2N0YXpRelp6TTFOR2RvZERRPT3jhYHjhLTjhYfjhY7jhYHjhLTjhYfjhLntmLjrgpjjhaPshJzjhZc07J=";
    private Key key;


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String reissuanceKakaoToken(String refreshToken) throws HttpClientErrorException.Unauthorized {
        String uri = TOKEN_URI + "?grant_type=refresh_token&client_id=" + CLIENT_ID + "&refresh_token=" + refreshToken;
        Flux<ReissuanceKakaoToken> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ReissuanceKakaoToken.class);

        return response.blockFirst().getAccess_token();
    }

    public String createToken(long userId){

        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration_time);   //만료 시간

        return Jwts.builder()
                .setSubject(String.valueOf(userId))       //토큰의 주체(주인?) 결정
                .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                .setExpiration(validity)                    //만료시간 설정
                .compact();                                 //토큰 생성
    }
}