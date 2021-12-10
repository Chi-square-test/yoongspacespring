package com.yoongspace.demo.security;

import com.yoongspace.demo.model.UserEntity;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    private static String SECRET_KEY;

    @Value("${jwt-tokens.value}")
    private void setSecretKey(String key){
        SECRET_KEY=key;
    }

    public String create(UserEntity userEntity){
        long expireTime =1000L * 10;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(userEntity.getStudentid())
                .setIssuer("Test yoongspace")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    public String createRefresh(UserEntity userEntity){
        long expireTime = 1000L * 60 * 24 * 2;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(userEntity.getStudentid())
                .setIssuer("Test yoongspace")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    public String validateAndGetUserId(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return  claims.getSubject();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

}
