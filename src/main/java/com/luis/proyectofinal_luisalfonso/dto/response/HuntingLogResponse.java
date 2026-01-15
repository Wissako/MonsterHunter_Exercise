package com.luis.proyectofinal_luisalfonso.dto.response;

import java.time.LocalDateTime;

public record HuntingLogResponse(
        Long id,
        LocalDateTime huntingDate,
        Integer timeTaken,
        Boolean successful,
        String hunterName,
        String questName,
        String monsterName
) {
}
