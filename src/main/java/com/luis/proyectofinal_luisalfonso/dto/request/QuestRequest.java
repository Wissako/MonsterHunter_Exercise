package com.luis.proyectofinal_luisalfonso.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record QuestRequest(
        @NotBlank(message = "El título de la misión es obligatorio")
        String name,

        @NotNull(message = "La dificultad es obligatoria")
        @Positive(message = "La dificultad debe ser mayor a 0")
        Integer difficulty,

        @Positive(message = "La recompensa debe ser positiva")
        Double reward,

        @NotNull(message = "Debes indicar el ID del monstruo objetivo")
        Long targetMonsterId
) {
}
