package com.example.userservice.KakaoAPI.Login.repository;

import com.example.userservice.KakaoAPI.Login.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDAO, String> {

}
