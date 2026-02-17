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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET_PHRASE = "esta_es_una_clave_muy_segura_y_larga_para_que_spring_no_se_queje_12345";

    @Getter
    private final SecretKey secretKey;

    public JwtService() {

        byte[] keyBytes = SECRET_PHRASE.getBytes(StandardCharsets.UTF_8);
        byte[] key256 = new byte[32];
        System.arraycopy(keyBytes, 0, key256, 0, 32);
        this.secretKey = Keys.hmacShaKeyFor(key256);
    }

    public String generateToken(Authentication authentication) {
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(authentication.getName())
                .issuer("gremio-monster-hunter")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .claim("roles", roles)
                .signWith(secretKey) // Usará HS256 con clave de 256 bits
                .compact();
    }
}