package com.example.userservice.Controller;

import com.example.userservice.Friends.service.KakaoFriendService;
import com.example.userservice.Friends.vo.KakaoFriendInfo;
import com.example.userservice.Login.token.KakaoTokenData;
import com.example.userservice.Login.token.KakaoToken;
import com.example.userservice.Login.service.KakaoUserService;
import com.example.userservice.Login.dto.ResponseKakaoUserInfo;
import com.example.userservice.Login.service.UserService;
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
