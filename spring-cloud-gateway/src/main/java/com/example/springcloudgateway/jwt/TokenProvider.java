package com.example.springcloudgateway.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@Slf4j
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long expiration_time;

    private Key key;

    public TokenProvider(
            @Value("${token.secret}") String secret,
            @Value("${token.expiration_time}") long expiration_time){
        this.secret = secret;
        this.expiration_time = expiration_time;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        //byte[] 형태의 시크릿 값을 HMAC-SHA 알고리즘의 키로 변환
        //JWT의 서명에 사용됨
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication){
        //authorities = 권한들을 받아옴
        //joining => 권한 요소들을 , 로 이어 붙여서 하나의 스트링으로 만듬
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.expiration_time);   //만료 시간

        return Jwts.builder()
                .setSubject(authentication.getName())       //토큰의 주체(주인?) 결정
                .claim(AUTHORITIES_KEY, authorities)        //권한 정보 설정
                .signWith(key, SignatureAlgorithm.HS512)    //서명 생성
                .setExpiration(validity)                    //만료시간 설정
                .compact();                                 //토큰 생성
    }

    // 토큰을 받아 클레임을 만들고 권한정보를 빼서 시큐리티 유저객체를 만들어 Authentication 객체 반환
    public Authentication getAuthentication(String token) {
        // 토큰 생성의 반대로 토큰에서 사용자 정보와 권한을 추출해서 사용
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        //이어붙인 권한을 다시 배열로
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 디비를 거치지 않고 토큰에서 값을 꺼내 바로 시큐리티 유저 객체를 만들어 Authentication을 만들어 반환하기에 유저네임, 권한 외 정보는 알 수 없다.
        User principal = new User(claims.getSubject(), "", authorities);

        //인증 결과 = 유저정보, 토큰, 권한을 이용해서 권한을 이용해서 반환
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            //키를 이용해서 JWT토큰 파싱
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
