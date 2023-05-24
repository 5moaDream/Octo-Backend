package com.example.userservice.service;

import com.example.userservice.dto.KakaoFriend;
import com.example.userservice.dto.KakaoTokenDTO;
import com.example.userservice.dto.KakaoUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class KakaoService {
    private final String FRIENDS_INFO_URI = "https://kapi.kakao.com/v1/api/talk/friends";
    private final String USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";
    private final String TOKEN_URI = "https://kauth.kakao.com/oauth/token";

    private final String REDIRECT_URI = "http://3.39.126.140:8000/unauthorization/code";
    private final String CLIENT_ID = "09201921cd5e210ee03ddd019e256454";
    private final String GRANT_TYPE = "authorization_code";

    private final WebClient webClient = WebClient.create();



    /**카카오 토큰 발급*/
    public KakaoTokenDTO getKakaoToken(String code) {
        String uri = TOKEN_URI + "?grant_type=" + GRANT_TYPE + "&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&code=" + code;

        Flux<KakaoTokenDTO> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenDTO.class);

        return response.blockFirst();
    }

    /**카카오 토큰 재발급*/
    public String renewalKakaoToken(String refreshToken) throws HttpClientErrorException.Unauthorized {
        String uri = TOKEN_URI + "?grant_type=refresh_token&client_id=" + CLIENT_ID + "&refresh_token=" + refreshToken;
        Flux<KakaoTokenDTO> response = webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(KakaoTokenDTO.class);

        return response.blockFirst().getAccess_token();
    }


    /**카카오 유저 정보 조회*/
    public KakaoUserDTO getKakaoUserInfo(String access_token) throws Exception{
        Mono<KakaoUserDTO> accessTokenResponse = WebClient.create().get()
                .uri(USER_INFO_URI)
                .header("Authorization", "Bearer " + access_token)
                .retrieve()
                .bodyToMono(KakaoUserDTO.class);
        return accessTokenResponse.block();
    }

    /**카카오 친구 목록 조회*/
    public KakaoFriend getKakaoFriends(String accessToken, int offset) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(FRIENDS_INFO_URI).build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("kapi.kakao.com")
                        .path("/v1/api/talk/friends")
                        .queryParam("limit", 20)
                        .queryParam("offset", offset)
                        .build())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoFriend.class)
                .block();
    }

}