package com.luis.proyectofinal_luisalfonso.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtService {

    // 1. CLAVE FIJA: Vital para que no te de error 401 cada vez que reinicias
    private static final String SECRET_PHRASE = "esta_es_una_clave_muy_segura_y_larga_para_que_spring_no_se_queje_12345";

    @Getter
    private final SecretKey secretKey;

    public JwtService() {
        // Usamos la frase fija
        this.secretKey = Keys.hmacShaKeyFor(SECRET_PHRASE.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        // Spring ya devuelve los roles como "ROLE_ADMIN" o "ROLE_USER"
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

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