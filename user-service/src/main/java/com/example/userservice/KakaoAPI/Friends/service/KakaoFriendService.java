package com.example.userservice.KakaoAPI.Friends.service;

import com.example.userservice.KakaoAPI.Friends.vo.KakaoFriendInfo;
import com.example.userservice.KakaoAPI.Friends.vo.ResponseKakaoFriends;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class KakaoFriendService {
    private final WebClient webClient = WebClient.create();
    private static final String FRIENDS_INFO_URI = "https://kapi.kakao.com/v1/api/talk/friends";

    public List<KakaoFriendInfo> getFriendsInfo(String token, Integer offset ) {
        String uri = FRIENDS_INFO_URI;

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(uri);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(uri).build();

        return  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("limit", 20)
                        .queryParam("offset", offset)
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(ResponseKakaoFriends.class)
                .block()
                .getFriends();
    }
}
