package com.example.userservice.Controller;

import com.example.userservice.Friends.service.KakaoFriendService;
import com.example.userservice.Friends.dto.FriendDTO;
import com.example.userservice.Friends.dto.KakaoFriend;
import com.example.userservice.Login.domain.UserEntity;
import com.example.userservice.Login.service.KakaoUserService;
import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.Login.service.UserService;
import com.example.userservice.setting.domain.SettingEntity;
import com.example.userservice.setting.dto.FirstSettingDTO;
import com.example.userservice.setting.service.SettingService;
import com.example.userservice.token.dto.KakaoTokenDTO;
import com.example.userservice.token.dto.RequestKakaoToken;
import com.example.userservice.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
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

    private final SettingService settingService;

    //테스트 용 토큰 발급
    @GetMapping("/code")
    public void code(@RequestParam("code") String code){
        System.out.println(code);
        KakaoTokenDTO token = tokenService.getToken(code);
        System.out.println(token.getAccess_token());
        System.out.println(token.getRefresh_token());
    }

    /**카카오 로그인*/
    @GetMapping("/kakao-login")
    public ResponseEntity<UserEntity> kakaoLogin(@RequestBody RequestKakaoToken kakaoToken) {
        //토큰 디비 구축
        //1) access token과 refresh 토큰을 받는다.

        //2) access token으로 유저 정보 반환
        String kakao_access_token = kakaoToken.getAccess_token();

        KakaoUserDTO kakaoUserDTO = kakaoUserService.getUserInfo(kakao_access_token);

        //2-1) 토큰 만료 시 재발급 후 조회
        if(kakaoUserDTO == null){
            kakao_access_token = tokenService.reissuanceKakaoToken(kakaoToken.getRefresh_token());
            kakaoUserDTO = kakaoUserService.getUserInfo(kakao_access_token);
        }

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

    /**유저정보 조회*/
    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUser(@RequestParam("userId") long userId){
        UserEntity user = userService.findByUserId(userId).get();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**첫 로그인 시 유저정보 세팅*/
    @PutMapping("/first")
    public HttpStatus firstSetting(@RequestBody FirstSettingDTO firstSettingDTO){
        SettingEntity settingEntity;
        Date afterMonth;

        //한달 뒤 날짜 생성
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        afterMonth = cal.getTime();

        settingEntity = SettingEntity.builder()
                        .userId(firstSettingDTO.getUserId())
                        .sleepTime(firstSettingDTO.getSleepTime())
                        .distance(firstSettingDTO.getDistance())
                        .sleepDate(afterMonth)
                        .runningDate(afterMonth)
                        .build();

        settingService.updateSettingInfo(settingEntity);
        userService.updateUser(firstSettingDTO.getUserId(), firstSettingDTO.getCharacterName());

        return HttpStatus.OK;
    }

    /**유저 세팅정보 업데이트*/
    @PutMapping("/setting")
    public HttpStatus updateSetting(@RequestBody SettingEntity setting){
        settingService.updateSettingInfo(setting);
        return HttpStatus.OK;
    }

    /**세팅 정보 조회*/
    @GetMapping("/setting")
    public ResponseEntity<SettingEntity> updateSetting(@RequestParam("userId") Long userId){
        SettingEntity setting = settingService.getSettingInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(setting);
    }

    /**친구 정보 불러오기*/
    @GetMapping("/kakao-friends")
    public List<FriendDTO> findKakaoFriendsList(@RequestBody RequestKakaoToken kakaoToken, @RequestParam("offset") int offset){
        String accessToken = kakaoToken.getAccess_token();
        KakaoFriend responseFriends = kakaoFriendService.getKakaoFriends(accessToken, offset);

        if(responseFriends == null){
            accessToken = tokenService.reissuanceKakaoToken(kakaoToken.getRefresh_token());
            responseFriends = kakaoFriendService.getKakaoFriends(accessToken, offset);
        }

        return kakaoFriendService.getFriends(responseFriends);
    }
}
