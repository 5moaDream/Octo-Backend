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
import com.example.userservice.token.dto.TokenSet;
import com.example.userservice.token.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@RestController
@RequiredArgsConstructor
public class UserController {
    private final KakaoUserService kakaoUserService;
    private final KakaoFriendService kakaoFriendService;

    private final UserService userService;
    private final TokenService tokenService;

    private final SettingService settingService;

    //테스트 용 토큰 발급
    @GetMapping("/unauthorization/code")
    public String code(@RequestParam("code") String code){
        System.out.println(code);
        KakaoTokenDTO token = tokenService.getToken(code);
        System.out.println(token.getAccess_token());
        System.out.println(token.getRefresh_token());
        return "엑세스 토큰: " + token.getAccess_token() + "\n리프레쉬 토큰: " + token.getRefresh_token();
    }

    /**서버 토큰 재발급*/
    @GetMapping("/unauthorization/refresh")
    public ResponseEntity tokenRefresh(@RequestHeader(value = "Authorization") String token){
        token = token.replace("Bearer ", "");
        String newToken = tokenService.reissuanceToken(token);

        if(newToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.status(HttpStatus.CREATED).header("Authorization", newToken).body(null);
    }

    /**카카오 로그인(서버 토큰 발급)*/
    @GetMapping("/unauthorization/kakao-login")
    public ResponseEntity<TokenSet> kakaoLogin(@RequestHeader(value = "Authorization") String kakaoToken) {
        kakaoToken = kakaoToken.replace("Bearer ", "");
        //토큰 디비 구축
        //1) access token과 refresh 토큰을 받는다.

        //2) access token으로 유저 정보 반환
        KakaoUserDTO kakaoUserDTO = kakaoUserService.getKakaoUserInfo(kakaoToken);

        //3) 카카오 유저 정보를 못 받아왔다면 다시 로그인 요청
        if(kakaoUserDTO == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        //4) 카카오 유저 아이디로 DB에서 유저 정보 조회
        Optional<UserEntity> userEntity = userService.findByUserId(kakaoUserDTO.getId());

        //4-1) 유저 정보가 없다면 기본 틀로 저장 => 클라이언트에서 이름 같은 정보들이 null이면 기본 설정하도록
        if(!userEntity.isPresent()){
            userService.createUser(kakaoUserDTO);
        }

        //5) 카카오 유저아이디로 토큰(access, refresh) 생성
        String access_token = tokenService.createToken(kakaoUserDTO.getId());
        System.out.println(access_token);
        String refreshToken = tokenService.createRefreshToken(kakaoUserDTO.getId());
        System.out.println(refreshToken);

        TokenSet tokenSet = new TokenSet(access_token, refreshToken);

        //6) 발급 받은 토큰들 DB에 저장 & 응답
        return ResponseEntity.status(HttpStatus.OK).body(tokenSet);
    }

    /**메인화면 요청*/



    /**유저정보 조회*/    //아이디 없이 토큰만 보내서 유저정보 조회하도록
    @GetMapping("/user")
    public ResponseEntity<Optional<UserEntity>> getUser(@RequestParam("id") Long userId){
        Optional<UserEntity> user = userService.findByUserId(userId);
        //유저 정보가 없다면 클라이언트에서 첫 로그인 유저 정보 세팅으로 요청

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**첫 로그인 시 유저정보 세팅*/
    @PutMapping("/first")
    public HttpStatus firstSetting(@RequestParam("id") Long userId, @RequestBody FirstSettingDTO firstSettingDTO){
        SettingEntity settingEntity;
        Date afterMonth;

        //한달 뒤 날짜 생성
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        afterMonth = cal.getTime();

        settingEntity = SettingEntity.builder()
                        .userId(userId)
                        .sleepTime(firstSettingDTO.getSleepTime())
                        .distance(firstSettingDTO.getDistance())
                        .dday(afterMonth)
                        .build();

        settingService.updateSettingInfo(settingEntity);
        userService.updateUser(firstSettingDTO.getCharacterName(), firstSettingDTO.getUserId());

        return HttpStatus.OK;
    }

    /**유저 세팅정보 업데이트*/
    @PutMapping("/setting")
    public HttpStatus updateSetting(@RequestParam("id") Long userId, @RequestBody SettingEntity setting){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Date afterMonth = cal.getTime();

        setting.setDday(afterMonth);
        setting.setUserId(userId);

        settingService.updateSettingInfo(setting);
        return HttpStatus.OK;
    }

    /**세팅 정보 조회*/
    @GetMapping("/setting")
    public ResponseEntity<SettingEntity> getSetting(@RequestParam("id") Long userId){
        Optional<SettingEntity> setting = settingService.getSettingInfo(userId);
        return ResponseEntity.status(HttpStatus.OK).body(setting.get());
    }

    /**친구 정보 불러오기*/
    @GetMapping("/kakao-friends")
    public ResponseEntity<List<FriendDTO>> findKakaoFriendsList(@RequestBody TokenSet kakaoToken, @RequestParam("offset") int offset){
        String accessToken = kakaoToken.getAccess_token();
        KakaoFriend responseFriends = kakaoFriendService.getKakaoFriends(accessToken, offset);

        //토큰 재발급 후 다시 요청
        if(responseFriends == null){
            accessToken = tokenService.reissuanceKakaoToken(kakaoToken.getRefresh_token());
            responseFriends = kakaoFriendService.getKakaoFriends(accessToken, offset);
        }

        if(responseFriends == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        //카카오 친구 조회에 성공했지만 친구가 없을 때
        else if(responseFriends.getFriends() == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<FriendDTO> result =  kakaoFriendService.getFriends(responseFriends);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
