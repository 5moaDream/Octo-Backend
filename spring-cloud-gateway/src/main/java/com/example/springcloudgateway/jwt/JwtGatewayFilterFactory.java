package com.example.springcloudgateway.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class JwtGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtGatewayFilterFactory.Config> {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private TokenProvider tokenProvider;

    public JwtGatewayFilterFactory(TokenProvider tokenProvider) {
        super(Config.class);
        this.tokenProvider = tokenProvider;
    }

    public static class Config {
        // add configuration properties here
        // 필드 변수들이 들어가는 자리?
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String jwt = resolveToken(exchange.getRequest());
            String requestURI = exchange.getRequest().getURI().toString();

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Authentication authentication = tokenProvider.getAuthentication(jwt);
                SecurityContextImpl securityContext = new SecurityContextImpl();
                securityContext.setAuthentication(authentication);
                exchange.getAttributes().put(SecurityContext.class.getName(), Mono.just(securityContext));
                log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
            } else {
                log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
            }

            return chain.filter(exchange);
        };
    }

    private String resolveToken(ServerHttpRequest request) {
        List<String> authorizationHeaders = request.getHeaders().get(AUTHORIZATION_HEADER);
        if (authorizationHeaders != null && authorizationHeaders.size() > 0) {
            String bearerToken = authorizationHeaders.get(0);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }
        return null;
    }
}