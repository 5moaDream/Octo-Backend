package com.example.userservice.Login.service;

import com.example.userservice.Login.dao.UserDAO;
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
    public void createUser(String email) {
        UserDAO user = new UserDAO();
        user.setEmail(email);

        userRepository.save(user);
        log.info("새로운 회원 저장 완료");
    }

    public boolean findUser(String email){
        Optional<UserDAO> user = userRepository.findById(email);
        return user.isPresent();
    }

}
