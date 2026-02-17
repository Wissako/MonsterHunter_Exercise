package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.repositories.HunterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private HunterRepository hunterRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos al cazador por email
        Hunter hunter = hunterRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cazador no encontrado: " + email));

        // Lo convertimos a un usuario de Spring Security
        return User.builder()
                .username(hunter.getEmail())
                .password(hunter.getPassword())
                .roles(hunter.getRole()) // "ADMIN" o "USER"
                .build();
    }
}