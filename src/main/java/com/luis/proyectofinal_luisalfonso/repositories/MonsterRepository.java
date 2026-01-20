package com.luis.proyectofinal_luisalfonso.repositories;

import com.luis.proyectofinal_luisalfonso.models.enums.HabitatName;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.proyectofinal_luisalfonso.models.entities.Monster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonsterRepository extends JpaRepository<Monster, Long> {

List<Monster> findByNameContainingIgnoreCase(String name);
List<Monster> findByWeakness(String weakness);
List<Monster>findByHabitats_Zone(HabitatName zone);

// Consulta compleja: Buscar monstruos por debilidad y zona de hábitat con uso de JOIN FETCH para evitar N+1 y LOWER CONCAT
@Query("SELECT DISTINCT m FROM Monster m " +
            "JOIN FETCH m.habitats h " +
            "WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND LOWER(m.weakness) = LOWER(:weakness) " +
            "AND h.zone = :zone")
    List<Monster> findComplexSearch(@Param("name") String name,
                                    @Param("weakness") String weakness,
                                    @Param("zone") HabitatName zone);

}

