package com.example.springcloudgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    //Spring security는 web에서 쓰기 때문에 webflux를 쓰는 gateway에선 에러가 발생한다.

    //config class는 security 설정을 하기위해 생성
    @Bean
    //SecurityWebFilterChain = webflux에서 사용된다.
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        //user/login 요청만 허용하고 나머진 전부 인증
        //csrf.disable => csrf는 위조 요청을 보내는 공격인데 위조요청을 보낼 수 있는 post, put, delete 요청을 막는다.
        //여기선 post, put, delete를 써야하기 때문에 disable로 비활성화 시킨다.
        return http
                .authorizeExchange()
                .pathMatchers("/user/login").permitAll()
                .anyExchange().authenticated()
                .and()
                .csrf().disable()
                .build();
    }
}