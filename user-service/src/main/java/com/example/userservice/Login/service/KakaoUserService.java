package com.example.userservice.Login.service;

import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.Login.token.TokenService;
import com.example.userservice.Login.token.requestKakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class KakaoUserService {
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient = WebClient.create();
    private final TokenService tokenService;


    public KakaoUserDTO getUserInfo(requestKakaoToken kaKaoToken){
        String uri = USER_INFO_URI;

        //1. 카카오 서버에 유저정보 조회 요청
        Mono<KakaoUserDTO> accessTokenResponse = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + kaKaoToken.getAccess_token())
                .retrieve()
                .bodyToMono(KakaoUserDTO.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    //2. Access Token이 만료된 경우, Refresh Token을 이용하여 Access Token을 갱신
                    if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        try {
                            String new_token = tokenService.reissuanceKakaoToken(kaKaoToken.getRefresh_token());

                            // 3-1 Access Token 갱신 성공 시, 사용자 정보를 다시 가져오기
                            return webClient.get()
                                    .uri(uri)
                                    .header("Authorization", "Bearer " + new_token)
                                    .retrieve()
                                    .bodyToMono(KakaoUserDTO.class);

                        } catch (HttpClientErrorException.Unauthorized ex) {
                            // 3-2 Refresh Token도 만료된 경우, 사용자를 다시 로그인하도록 유도
                            return Mono.empty();
                        }
                    } else {
                        // 다른 예외 발생 시, Mono.empty() 리턴
                        return Mono.empty();
                    }
                });

        return accessTokenResponse.block();
    }
}
