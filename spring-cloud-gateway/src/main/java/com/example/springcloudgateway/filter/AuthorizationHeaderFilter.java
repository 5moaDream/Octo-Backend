package com.example.springcloudgateway.filter;

import com.example.springcloudgateway.jwt.RefreshTokenEntity;
import com.example.springcloudgateway.jwt.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;
    RefreshTokenRepository repository;
    private Key key;

    public AuthorizationHeaderFilter(Environment env, RefreshTokenRepository repository){
        super(Config.class);    //super = 상속해준 부모
        this.env = env;
        this.repository = repository;
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("token.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public static class Config{

    }

    //login -> token -> users (with token) -> header(include token)
    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String new_accessToken = null;

            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "no authorization in header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");

            //토큰 유효성 검사
            new_accessToken = isJwtValid(jwt);
            if(new_accessToken.isEmpty()){
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            //새로운 토큰을 헤더에 넣음
            exchange.getResponse().getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + new_accessToken);
            return chain.filter(exchange);
        });
    }

    private String isJwtValid(String jwt) {
        Long userId = null;
        Date expire = null;
        String new_accessToken = null;
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(env.getProperty("token.secret"))
                    .build()
                    .parseClaimsJws(jwt);

            userId = Long.parseLong(jws.getBody().getSubject());
            expire = jws.getBody().getExpiration();
        } catch (Exception ex) {
            return null;
        }

        //subject에 아무것도 없을 때
        if (userId == null)
             return null;

        Date now = new Date();
        //토큰 만료
        if (now.after(expire)){
            RefreshTokenEntity refreshToken = repository.findRefreshTokenEntityByUserIdAndValidityAfter(userId, now);
            if(refreshToken.getUserId() == userId)
                new_accessToken = createToken(userId);
        }
        return new_accessToken;
    }

    public String createToken(long userId){
        log.info("토큰 생성요청");

        long now = (new Date()).getTime();
        Date validity = new Date(now + Long.parseLong(env.getProperty("token.expiration_time")));   //만료 시간

        return Jwts.builder()
                .setSubject(String.valueOf(userId))       //토큰의 주체(주인?) 결정
                .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                .setExpiration(validity)                    //만료시간 설정
                .compact();                                 //토큰 생성
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);

        return response.setComplete();
    }
}
