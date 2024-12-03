package com.sparta.msa_exam.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilterFactory implements GatewayFilterFactory<Object> {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public JwtAuthenticationFilterFactory(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> jwtAuthenticationFilter.filter(exchange, chain);
    }

    @Override
    public Class<Object> getConfigClass() {
        return Object.class;  // 설정 클래스가 없다면 Object.class를 반환
    }
}