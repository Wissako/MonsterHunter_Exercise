package com.luis.proyectofinal_luisalfonso.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Getter
    private final SecretKey secretKey;

    public JwtService() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String generateToken(Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")); // Espacio en lugar de coma suele dar menos problemas

        return Jwts.builder()
                .subject(authentication.getName())
                .issuer("gremio-monster-hunter")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .claim("roles", roles)
                .signWith(secretKey)
                .compact();
    }
}