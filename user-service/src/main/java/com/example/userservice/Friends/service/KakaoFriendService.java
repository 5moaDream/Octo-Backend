package com.example.userservice.Friends.service;

import com.example.userservice.Friends.vo.FriendDTO;
import com.example.userservice.Friends.vo.KakaoFriend;
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
    private final WebClient webClient = WebClient.create();
    private final UserRepository repository;

    private static final String FRIENDS_INFO_URI = "https://kapi.kakao.com/v1/api/talk/friends";

    public KakaoFriend getKakaoFriends(String token, Integer offset) {
        //카톡 서버에서 friend id를 가져와서 DB에서 user 정보 조회
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
                .bodyToMono(KakaoFriend.class)
                .block();
    }

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
                    FriendDTO friend = new FriendDTO(u.getUserId(), k.getProfile_nickname(), u.getCharacter_url());
                    responseFriendList.add(friend);
                    break;
                }
            }
        }
        return responseFriendList;
    }
}
