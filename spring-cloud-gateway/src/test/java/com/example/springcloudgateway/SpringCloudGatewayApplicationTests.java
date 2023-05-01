package com.example.springcloudgateway;

import com.example.springcloudgateway.jwt.RefreshTokenEntity;
import com.example.springcloudgateway.jwt.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@SpringBootTest
class SpringCloudGatewayApplicationTests {
    @Autowired
    RefreshTokenRepository repository;
    @Test
    void contextLoads() {
    }

    @Test
    void 리프레시_토큰_조회_테스트(){
        Duration oneMonth = Duration.ofDays(30);
        Date validity = Date.from(Instant.now().plus(oneMonth));

        RefreshTokenEntity refreshToken = repository.findRefreshTokenEntityByUserIdAndValidityAfter(2713582482L, new Date());
        //new Date() = 현재 시간, validity = 한달 후
        System.out.println(refreshToken);
    }
}
