package com.example.userservice.Friends.service;

import com.example.userservice.Friends.dto.FriendDTO;
import com.example.userservice.Friends.dto.KakaoFriend;
import com.example.userservice.Login.domain.UserEntity;
import com.example.userservice.Login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class KakaoFriendService {
    private final UserRepository repository;
    private static final String FRIENDS_INFO_URI = "https://kapi.kakao.com/v1/api/talk/friends";

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

    /**카카오 친구 userId와 DB의 userId가 일치하는 유저들 조회*/
    public List<FriendDTO> getFriends(KakaoFriend friends){
        KakaoFriend.Friend[] friendArray = friends.getFriends();
        List<Long> friendIds = new ArrayList<>();
        List<FriendDTO> responseFriendList = new ArrayList<>();

        //where 절에 넣을 id list 생성
        for(int i=0; i<friendArray.length; i++)
            friendIds.add(friendArray[i].getId());

        //친구 유저를 다 찾아옴
        List<UserEntity> userList = repository.findAllByUserIdIn(friendIds);

        //유저 DTO list 생성
        for(UserEntity u : userList){
            for(KakaoFriend.Friend k : friends.getFriends()){
                if(k.getId() == u.getUserId()){
                    FriendDTO friend = new FriendDTO(u.getUserId(), k.getProfile_nickname(), u.getCharacterUrl());
                    responseFriendList.add(friend);
                    break;
                }
            }
        }
        return responseFriendList;
    }
}
