package com.example.userservice.Login.token;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

// 인가코드를 이용하여 Token ( Access , Refresh )를 받는다.
@Component
@RequiredArgsConstructor
public class KakaoTokenData {
    private final WebClient webClient = WebClient.create();
    private static final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String REDIRECT_URI = "http://127.0.0.1:8080/kakao-login";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String CLIENT_ID = "09201921cd5e210ee03ddd019e256454";

    public KakaoToken getToken(String code) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;

        Flux<KakaoToken> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoToken.class);

        return response.blockFirst();
    }
}