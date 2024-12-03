package com.sparta.msa_exam.gateway.service;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);  // 토큰을 파싱해서 예외가 발생하지 않으면 유효함
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
