package com.example.userservice.Login.service;

import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.token.TokenService;
import com.example.userservice.token.RequestKakaoToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Slf4j
public class KakaoUserService {
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient = WebClient.create();
    private final TokenService tokenService;


    public KakaoUserDTO getUserInfo(RequestKakaoToken kaKaoToken){
        String uri = USER_INFO_URI;

        log.info("유저 정보 조회 요청");

        //1. 카카오 서버에 유저정보 조회 요청
        Mono<KakaoUserDTO> accessTokenResponse = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + kaKaoToken.getAccess_token())
                .retrieve()
                .bodyToMono(KakaoUserDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    return Mono.empty();
                });

        return accessTokenResponse.block();
    }
}
