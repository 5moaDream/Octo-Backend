package com.example.userservice.KakaoAPI.Controller;

import com.example.userservice.KakaoAPI.Friends.service.KakaoFriendService;
import com.example.userservice.KakaoAPI.Friends.vo.KakaoFriendInfo;
import com.example.userservice.KakaoAPI.Login.token.KakaoTokenData;
import com.example.userservice.KakaoAPI.Login.token.KakaoToken;
import com.example.userservice.KakaoAPI.Login.service.KakaoUserService;
import com.example.userservice.KakaoAPI.Login.dto.ResponseKakaoUserInfo;
import com.example.userservice.KakaoAPI.Login.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoController {

    private final KakaoTokenData kakaoTokenJsonData;
    private final KakaoUserService kakaoUserInfo;
    private final KakaoFriendService kakaoFriendsInfo;

    private final UserService userService;


    @GetMapping("/kakao-login")
    @ResponseBody
    public ResponseKakaoUserInfo kakaoLogin(@RequestParam("code") String code) {
        KakaoToken kakaoTokenResponse = kakaoTokenJsonData.getToken(code);

        ResponseKakaoUserInfo userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());

        System.out.println(kakaoTokenResponse.getAccess_token());

        userService.createUser(userInfo.getKakao_account().getEmail(),
                                userInfo.properties.getNickname(),
                                userInfo.properties.getProfile_image());

        return userInfo;
    }

    @GetMapping("/kakao-friends")
    @ResponseBody
    public List<KakaoFriendInfo> findKakaoFriendsList(@RequestParam("token") String token, @RequestParam("offset") Integer offset){
        return kakaoFriendsInfo.getFriendsInfo(token, offset);
    }
}
