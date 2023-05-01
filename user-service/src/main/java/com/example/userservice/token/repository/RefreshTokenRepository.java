package com.example.userservice.token.repository;

import com.example.userservice.token.domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    RefreshTokenEntity findByUserIdAndExpirationTimeAfter(Long userId, Date expiration_time);
}
