package com.example.userservice.KakaoAPI.Login.service;

import com.example.userservice.KakaoAPI.Login.dto.ResponseKakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class KakaoUserService {
    private final WebClient webClient = WebClient.create();
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    public ResponseKakaoUserInfo getUserInfo(String token) {
        String uri = USER_INFO_URI;

        Mono<ResponseKakaoUserInfo> response = webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ResponseKakaoUserInfo.class);

        return response.block();
    }
}
