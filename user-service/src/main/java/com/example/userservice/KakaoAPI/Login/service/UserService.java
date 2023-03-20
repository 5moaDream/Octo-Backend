package com.example.userservice.KakaoAPI.Login.service;

import com.example.userservice.KakaoAPI.Login.dao.UserDAO;
import com.example.userservice.KakaoAPI.Login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long createUser(String email, String nickname, String profile) {
        UserDAO user = UserDAO.builder()
                .email(email)
                .nickname(nickname)
                .profile_image(profile)
                .build();

        userRepository.save(user);
        log.info("새로운 회원 저장 완료");
        return user.getId();
    }
}
