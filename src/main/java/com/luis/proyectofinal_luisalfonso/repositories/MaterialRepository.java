package com.luis.proyectofinal_luisalfonso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.proyectofinal_luisalfonso.models.entities.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {

}

