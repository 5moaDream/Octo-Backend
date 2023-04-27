package com.example.userservice.Login.service;

import com.example.userservice.Login.dto.KakaoUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
@Slf4j
public class KakaoUserService {
    private static final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private final WebClient webClient = WebClient.create();


    public KakaoUserDTO getUserInfo(String access_token){
        Mono<KakaoUserDTO> accessTokenResponse = webClient.get()
                .uri(USER_INFO_URI)
                .header("Authorization", "Bearer " + access_token)
                .retrieve()
                .bodyToMono(KakaoUserDTO.class);
        return accessTokenResponse.block();
    }
}
