package com.luis.proyectofinal_luisalfonso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import org.springframework.stereotype.Repository;

@Repository
public interface HunterRepository extends JpaRepository<Hunter, Long> {

    boolean findByEmail(String email);
}

