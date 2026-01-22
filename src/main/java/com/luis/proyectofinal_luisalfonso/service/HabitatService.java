package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.models.entities.Habitat;
import com.luis.proyectofinal_luisalfonso.repositories.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitatService {
    @Autowired
        private HabitatRepository habitatRepository;
    public List<Habitat> getAllHabitat() {
        return habitatRepository.findAll();
    }

}
