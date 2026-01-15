package com.luis.proyectofinal_luisalfonso.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HuntingLogRequest(
        @NotNull(message = "El ID del cazador es obligatorio")
        Long hunterId,

        @NotNull(message = "El ID de la misión es obligatorio")
        Long questId,

        @Positive(message = "El tiempo empleado debe ser mayor a 0 minutos")
        Integer timeTaken,

        @NotNull(message = "Debes indicar si fue exitosa o no")
        Boolean successful
) {
}
