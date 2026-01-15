package com.luis.proyectofinal_luisalfonso.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Range;

public record HunterRequest(
        @NotBlank(message = "El nombre del cazador es obligatorio")
        String name,

        @PositiveOrZero(message = "El rango debe ser positivo")
        @Range(min = 1, max = 999, message = "El rango debe estar entre 1 y 999")
        Integer rank,

        @NotBlank(message = "El arma principal es obligatoria")
        String mainWeapon,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email
) {
}
