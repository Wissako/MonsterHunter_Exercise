package com.luis.proyectofinal_luisalfonso.repositories;

import com.luis.proyectofinal_luisalfonso.models.enums.HabitatName;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.proyectofinal_luisalfonso.models.entities.Monster;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {

List<Monster> findByNameContainingIgnoreCase(String name);
List<Monster> findByWeakness(String weakness);
List<Monster>findByHabitats_Zone(HabitatName zone);
}

