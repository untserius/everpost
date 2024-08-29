package com.blogapp.service.impl;

import com.blogapp.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import java.util.Date;

public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expiry}")
    private String expiryTime;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secretkey}")
    private String secretKey;

    @Override
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .claim("authorities",authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime))
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
