package com.example.springcloudgateway.jwt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    RefreshTokenEntity findRefreshTokenEntityByUserIdAndValidityAfter(Long userId, Date expiration_time);
}
