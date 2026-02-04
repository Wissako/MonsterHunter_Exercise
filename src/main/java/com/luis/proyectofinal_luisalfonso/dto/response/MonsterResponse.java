package com.luis.proyectofinal_luisalfonso.dto.response;

import java.util.List;

public record MonsterResponse(
        Long id,
        String name,
        String type,
        String element,
        String weakness,
        Integer threatLevel,
        String imageUrl,
        List<String> materials,
        List<String> habitats
) {
}
