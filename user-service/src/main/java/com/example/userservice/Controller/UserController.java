package com.example.userservice.Controller;

import com.example.userservice.Friends.service.KakaoFriendService;
import com.example.userservice.Friends.vo.KakaoFriendInfo;
import com.example.userservice.Login.domain.UserEntity;
import com.example.userservice.Login.service.KakaoUserService;
import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.Login.service.UserService;
import com.example.userservice.Login.token.requestKakaoToken;
import com.example.userservice.Login.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final KakaoUserService kakaoUserService;
    private final KakaoFriendService kakaoFriendsInfo;

    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/kakao-login")
    public ResponseEntity<UserEntity> kakaoLogin(@RequestBody requestKakaoToken kakaoToken) {
        //1) access token과 refresh 토큰을 받는다.

        //2) access token으로 유저 정보 반환 & token 만료 시 refresh 토큰으로 재발급해서 유저정보 반환
        KakaoUserDTO kakaoUserDTO = kakaoUserService.getUserInfo(kakaoToken);

        //3) 카카오 유저 정보를 못 받아왔다면 다시 로그인 요청
        if(kakaoUserDTO == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        //4) 카카오 유저아이디로 토큰 생성 ( 유저 아이디 )
        String access_token = tokenService.createToken(kakaoUserDTO.getId());

        //5) 카카오 유저 아이디로 DB에서 유저 정보 조회
        UserEntity userEntity = userService.findByUserId(kakaoUserDTO.getId());

        //5-1) 유저 정보가 없다면 기본 틀로 저장 => 클라이언트에서 이름 같은 정보들이 null이면 기본 설정하도록
        if(userEntity == null)
            userEntity = userService.createUser(kakaoUserDTO);

        //6) 발급 받은 토큰들 DB에 저장 & 응답 헤더에 access token 추가
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + access_token)
                .body(userEntity);
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser(@RequestParam("userId") long userId){
        UserEntity user = userService.findByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/user")
    public HttpStatus setUser(@RequestParam("character_name") String characterName, @RequestParam("userId") long userId){
        userService.updateUser(characterName, userId);
        return HttpStatus.OK;
    }

    @GetMapping("/kakao-friends")
    public List<KakaoFriendInfo> findKakaoFriendsList(@RequestParam("token") String token, @RequestParam("offset") Integer offset){
        //토큰은 filter로 체크 -> 클라이언트에서 저장할 방법을 정해야함. ex) 쿠키, 세션
        return kakaoFriendsInfo.getFriendsInfo(token, offset);
    }
}
