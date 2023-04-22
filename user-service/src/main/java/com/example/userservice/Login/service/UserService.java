package com.example.userservice.Login.service;

import com.example.userservice.Login.domain.UserEntity;
import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.Login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserEntity createUser(KakaoUserDTO kakaoUserDTO) {
        String default_character_url = "/Users/jeongchan-yeong/Documents/GitHub/Octo-Backend/sql/test_image/문어1.png";

        UserEntity user = UserEntity.builder()
                            .userId(kakaoUserDTO.getId())
                            .thumbnail_image_url(kakaoUserDTO.getKakao_account().getProfile().getThumbnail_image_url())
                            .experienceValue(0)
                            .character_url(default_character_url)
                            .build();

        UserEntity result = userRepository.save(user);
        return result;
    }

    public Optional<UserEntity> findByUserId(long userId){
        log.info("DB에서 유저 조회 요청");

        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user;
    }


    public void updateUser(String characterName, long userId){
        userRepository.updateCharacterName(characterName, userId);
    }
}
