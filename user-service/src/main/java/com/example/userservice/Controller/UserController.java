package com.example.userservice.Controller;

import com.example.userservice.Friends.service.KakaoFriendService;
import com.example.userservice.Friends.vo.FriendDTO;
import com.example.userservice.Friends.vo.KakaoFriend;
import com.example.userservice.Login.domain.UserEntity;
import com.example.userservice.Login.service.KakaoUserService;
import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.Login.service.UserService;
import com.example.userservice.token.KakaoTokenDTO;
import com.example.userservice.token.RequestKakaoToken;
import com.example.userservice.token.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final KakaoUserService kakaoUserService;
    private final KakaoFriendService kakaoFriendService;

    private final UserService userService;
    private final TokenService tokenService;


    //토큰 테스트 용
    @GetMapping("/code")
    public void code(@RequestParam("code") String code){
        System.out.println(code);
        KakaoTokenDTO token = tokenService.getToken(code);
        System.out.println(token.getAccess_token());
        System.out.println(token.getRefresh_token());
    }

    @GetMapping("/kakao-login")
    public ResponseEntity<UserEntity> kakaoLogin(@RequestBody RequestKakaoToken kakaoToken) {
        //토큰 디비 구축
        //1) access token과 refresh 토큰을 받는다.

        //2) access token으로 유저 정보 반환
        //token 만료 시 클라이언트에서 refresh 토큰으로 재발급해서 다시 요청
        KakaoUserDTO kakaoUserDTO = kakaoUserService.getUserInfo(kakaoToken);

        //3) 카카오 유저 정보를 못 받아왔다면 다시 로그인 요청
        if(kakaoUserDTO == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        //4) 카카오 유저 아이디로 DB에서 유저 정보 조회
        Optional<UserEntity> userEntity = userService.findByUserId(kakaoUserDTO.getId());

        //4-1) 유저 정보가 없다면 기본 틀로 저장 => 클라이언트에서 이름 같은 정보들이 null이면 기본 설정하도록
        if(!userEntity.isPresent())
            userEntity = Optional.ofNullable(userService.createUser(kakaoUserDTO));

        //5) 카카오 유저아이디로 토큰(access, refresh) 생성
        String access_token = tokenService.createToken(kakaoUserDTO.getId());


        //6) 발급 받은 토큰들 DB에 저장 & 응답 헤더에 access token 추가
        return ResponseEntity.status(HttpStatus.OK)
                .header("Authorization", "Bearer " + access_token)
                .body(userEntity.get());
    }

    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser(@RequestParam("userId") long userId){
        UserEntity user = userService.findByUserId(userId).get();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/user")
    public HttpStatus setUser(@RequestParam("character_name") String characterName, @RequestParam("userId") long userId){
        userService.updateUser(characterName, userId);
        return HttpStatus.OK;
    }

    @GetMapping("/kakao-friends")
    public List<FriendDTO> findKakaoFriendsList(@RequestParam("token") String token, @RequestParam("offset") Integer offset){
        //kakao token + server token
        KakaoFriend friend =  kakaoFriendService.getKakaoFriends(token, offset);
        return kakaoFriendService.getFriends(friend);
    }
}
