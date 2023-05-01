package com.example.userservice.Login.service;

import com.example.userservice.Login.domain.UserEntity;
import com.example.userservice.Login.dto.KakaoUserDTO;
import com.example.userservice.Login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity createUser(KakaoUserDTO kakaoUserDTO) {
        String default_character_url = "https://octo-image-bucket.s3.ap-northeast-2.amazonaws.com/%EA%B8%B0%EB%B3%B8%20%EB%AC%B8%EC%96%B4.png";

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
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user;
    }

    public void updateUser(String characterName, Long userId){
        userRepository.updateCharacterName(characterName, userId);
    }
}
