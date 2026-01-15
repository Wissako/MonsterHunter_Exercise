package com.luis.proyectofinal_luisalfonso.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.luis.proyectofinal_luisalfonso.models.entities.HuntingLog;
import org.springframework.stereotype.Repository;

@Repository
public interface HuntingLogRepository extends JpaRepository<HuntingLog, Long> {

}

