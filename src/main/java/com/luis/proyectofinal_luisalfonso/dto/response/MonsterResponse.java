package com.luis.proyectofinal_luisalfonso.dto.response;

import java.util.List;

public record MonsterResponse(
        Long id,
        String name,
        String type,
        String element,
        String weakness,
        Integer threatLevel,
        List<String> materials,
        List<String> habitats
) {
}
