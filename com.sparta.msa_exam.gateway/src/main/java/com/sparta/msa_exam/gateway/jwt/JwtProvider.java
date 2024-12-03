package com.sparta.msa_exam.gateway.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${service.jwt.secret-key}")
    private String secretKey;

    @Value("${service.jwt.access-expiration}")
    private long expirationTime;

    // JWT 토큰 생성
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // JWT 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    // JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build();
            parser.parseClaimsJws(token);  // 토큰을 파싱해서 예외가 발생하지 않으면 유효함
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
