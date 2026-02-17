package com.luis.proyectofinal_luisalfonso.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "El email no puede estar vacío")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        @Size(min = 6,max = 80)
        String password
) {
}