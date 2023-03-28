package com.example.userservice.Login.repository;

import com.example.userservice.Login.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, String> {

}
