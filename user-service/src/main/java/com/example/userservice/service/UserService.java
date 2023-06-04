package com.example.userservice.service;

import com.example.userservice.domain.UserEntity;
import com.example.userservice.dto.FriendDTO;
import com.example.userservice.dto.KakaoFriend;
import com.example.userservice.dto.KakaoUserDTO;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final String basicCharUrl = "https://octo-image-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A1%E1%84%80%E1%85%B5%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%A5.png";

    /**
     * 유저 생성
     */
    public UserEntity createUser(KakaoUserDTO kakaoUserDTO) {
        UserEntity user = UserEntity.builder()
                .userId(kakaoUserDTO.getId())
                .thumbnailImageUrl(kakaoUserDTO.getKakao_account().getProfile().getThumbnail_image_url())
                .characterUrl(basicCharUrl)
                .experienceValue(0)
                .build();

        UserEntity result = userRepository.save(user);
        return result;
    }

    /**
     * 유저 조회
     */
    public Optional<UserEntity> findUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        return user;
    }

    /**
     * 목표 설정
     */
    public void updateTarget(Long userId, int sleepTime, float distance) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, +1);
        Date dday = cal.getTime();

        userRepository.updateTarget(userId, sleepTime, distance, dday);
    }

    /**
     * 상태 메세지 변경
     */
    public void updateStateMSG(Long userId, String stateMSG) {
        userRepository.updateStateMSG(userId, stateMSG);
    }

    /**
     * 캐릭터 이름 변경
     */
    public void updateCharacterName(Long userId, String stateMSG) {
        userRepository.updateCharacterName(userId, stateMSG);
    }


    /**캐릭터 변경*/
    public void updateCharacter(Long userId, String character) {
        userRepository.updateCharacter(userId, character);
    }


    /**
     * 친구 조회(카카오 친구로)
     */
    public List<FriendDTO> getFriends(KakaoFriend friends) {
        KakaoFriend.Friend[] elements = friends.getElements();
        List<Long> friendIds = new ArrayList<>();
        List<FriendDTO> responseFriendList = new ArrayList<>();

        //where 절에 넣을 id list 생성
        for (KakaoFriend.Friend friend : elements)
            friendIds.add(friend.getId());

        //친구 유저를 다 찾아옴
        List<UserEntity> userList = userRepository.findAllByUserIdIn(friendIds);

        //유저 DTO list 생성
        for (UserEntity u : userList) {
            for (KakaoFriend.Friend k : friends.getElements()) {
                if (k.getId() == u.getUserId()) {
                    FriendDTO friend = new FriendDTO(u.getUserId(), k.getProfile_nickname(), u.getCharacterUrl(), u.getCharacterName(), k.getProfile_thumbnail_image(), u.getStateMsg());
                    responseFriendList.add(friend);
                    break;
                }
            }
        }
        return responseFriendList;
    }
}
