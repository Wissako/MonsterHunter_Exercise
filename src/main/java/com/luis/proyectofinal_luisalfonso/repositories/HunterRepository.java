package com.luis.proyectofinal_luisalfonso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HunterRepository extends JpaRepository<Hunter, Long> {

    boolean existsByEmail(String email);

   Optional<Hunter> findByEmail(String email);
}

