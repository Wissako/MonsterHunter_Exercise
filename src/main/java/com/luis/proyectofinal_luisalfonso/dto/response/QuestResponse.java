package com.luis.proyectofinal_luisalfonso.dto.response;

public record QuestResponse(
        Long id,
        String name,
        Integer difficulty,
        Double reward,
        String targetMonsterName

) {
}
