package com.example.springcloud.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter(){
        super(Config.class);
    }

    public static class Config{
    }

    @Override
    public GatewayFilter apply(Config config){
        //request filter
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Request URI: {}", request.getURI());
            log.info("request id: {}", request.getId());

            //response filter
            return chain.filter(exchange).then(Mono.fromRunnable(()-> {
                log.info("response code: {}", response.getStatusCode());
            }));
        };
    }

}
