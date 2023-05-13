package com.example.userservice.repository;

import com.example.userservice.domain.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    RefreshTokenEntity findByUserIdAndExpirationTimeAfter(Long userId, Date expiration_time);
}
