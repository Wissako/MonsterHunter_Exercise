package com.luis.proyectofinal_luisalfonso.error;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " not found with id " + id);
    }
}

