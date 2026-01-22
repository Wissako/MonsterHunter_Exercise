package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.models.entities.Material;
import com.luis.proyectofinal_luisalfonso.repositories.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    public List<Material> getAll() {
        return materialRepository.findAll();
    }

}
