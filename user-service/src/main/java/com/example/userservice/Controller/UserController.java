package com.example.userservice.Controller;

import com.example.userservice.Friends.service.KakaoFriendService;
import com.example.userservice.Friends.vo.KakaoFriendInfo;
import com.example.userservice.Login.token.KakaoTokenData;
import com.example.userservice.Login.token.KakaoToken;
import com.example.userservice.Login.service.KakaoUserService;
import com.example.userservice.Login.dto.ResponseKakaoUserInfo;
import com.example.userservice.Login.service.UserService;
import com.example.userservice.Login.token.ResponseToken;
import com.example.userservice.octo.OctoRepository;
import com.example.userservice.octo.Octopus;
import com.example.userservice.octo.RequestOcto;
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

    private final KakaoTokenData kakaoTokenJsonData;
    private final KakaoUserService kakaoUserInfo;
    private final KakaoFriendService kakaoFriendsInfo;

    private final UserService userService;
    private final OctoRepository octoRepository;


    @GetMapping("/kakao-login")
    public ResponseEntity<ResponseToken> kakaoLogin(@RequestParam("code") String code) {
        KakaoToken kakaoTokenResponse = kakaoTokenJsonData.getToken(code);
        ResponseToken responseToken = new ResponseToken(kakaoTokenResponse.getAccess_token(), kakaoTokenResponse.getRefresh_token());

        ResponseKakaoUserInfo userInfo = kakaoUserInfo.getUserInfo(kakaoTokenResponse.getAccess_token());

        //사용자 이메일이 디비에 없다면 생성 & 클라이언트 단에서 문어id가 null일 때 생성하는 로직
        if(!userService.findUser(userInfo.kakao_account.email)){
            userService.createUser(userInfo.kakao_account.email);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseToken);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseToken);
    }

    //토큰 갱신
    //HTTP 401 응답 시 토큰 갱신 요청
    @GetMapping("/refresh_token")
    public ResponseEntity<String> refresh(@RequestParam("refresh_token") String refresh_token){
        String accessToken = kakaoTokenJsonData.updateToken(refresh_token);

        return ResponseEntity.status(HttpStatus.OK).body(accessToken);
    }

    @GetMapping("/kakao-friends")
    public List<KakaoFriendInfo> findKakaoFriendsList(@RequestParam("token") String token, @RequestParam("offset") Integer offset){
        //토큰은 filter로 체크 -> 클라이언트에서 저장할 방법을 정해야함. ex) 쿠키, 세션
        return kakaoFriendsInfo.getFriendsInfo(token, offset);
    }







    @PostMapping("/octo")
    public ResponseEntity createOctoById(@RequestBody RequestOcto octopus){
        Octopus octo = new Octopus(
                octopus.getUserId(),
                octopus.getName(),
                0, 0,
                octopus.getOctoKindId()
        );

        Octopus result = octoRepository.save(octo);

        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @GetMapping("/octo/{octoId}")
    public ResponseEntity<Octopus> findMainOctoById(@PathVariable Long octoId){
        Optional<Octopus> octo = octoRepository.findById(octoId);

        if(octo.get().equals(null))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.status(HttpStatus.OK).body(octo.get());
    }


    @GetMapping("/octo-collection/{userId}")
    public ResponseEntity<List<Octopus>> findAllOctoById(@PathVariable Long userId){
        List<Octopus> octos = octoRepository.findAllOctoById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(octos);
    }

    @PutMapping("/octo-name")
    public ResponseEntity<String> updateOctoName(){
        return null;
    }

    @PutMapping("/octo")
    public ResponseEntity<Octopus> updateMainOcto(){
        return null;
    }
}
