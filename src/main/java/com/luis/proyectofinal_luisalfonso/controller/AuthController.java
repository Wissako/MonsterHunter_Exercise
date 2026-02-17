package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.dto.request.LoginRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HunterResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import com.luis.proyectofinal_luisalfonso.repositories.HunterRepository;
import com.luis.proyectofinal_luisalfonso.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtService jwtService;
    @Autowired private HunterRepository hunterRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Esto verifica email y contraseña automáticamente usando UserDetailsService
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // Si pasa, generamos el token
        String token = jwtService.generateToken(authentication);
        return ResponseEntity.ok(Map.of("token", token));
    }

    // REGISTRO
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HunterRequest request) {
        if (hunterRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest().body("Email ya existe");
        }

        Hunter hunter = new Hunter();
        hunter.setName(request.name());
        hunter.setEmail(request.email());
        hunter.setRank(1);
        hunter.setMainWeapon(HunterWeapons.GREAT_SWORD); // O lo que venga del request
        hunter.setRole("ADMIN"); // EN ADMIN POR AHORA PARA HACER PRUEBAS!

        // ¡Cifrar contraseña!
        hunter.setPassword(passwordEncoder.encode(request.password()));

        hunterRepository.save(hunter);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cazador registrado");
    }
}