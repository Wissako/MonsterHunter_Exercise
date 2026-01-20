package com.luis.proyectofinal_luisalfonso.dto.response;

public record HunterResponse(
        Long id,
        String name,
        Integer rank,
        String mainWeapon,
        String email
) {
}
