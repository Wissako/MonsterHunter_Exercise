package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.models.entities.Habitat;
import com.luis.proyectofinal_luisalfonso.repositories.HabitatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/habitats")
class HabitatController {
    @Autowired
        private HabitatRepository habitatRepository;
    @GetMapping
        public ResponseEntity<List<Habitat>> getAllHabitat() {
        return ResponseEntity.ok(habitatRepository.findAll());
    }

}
