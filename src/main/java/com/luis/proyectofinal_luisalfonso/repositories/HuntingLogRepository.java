package com.luis.proyectofinal_luisalfonso.repositories;

import com.luis.proyectofinal_luisalfonso.models.entities.HuntingLog; // <--- Importante: ENTIDAD
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HuntingLogRepository extends JpaRepository<HuntingLog, Long> {


    List<HuntingLog> findByHunter_Id(Long hunterId);
}