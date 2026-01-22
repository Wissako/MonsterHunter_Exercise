package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.models.entities.Material;
import com.luis.proyectofinal_luisalfonso.repositories.MaterialRepository;
import com.luis.proyectofinal_luisalfonso.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/materials")
class MaterialController {
@Autowired
        private MaterialService materialService;
@GetMapping
        public ResponseEntity<List<Material>> getAll(){
    return ResponseEntity.ok(materialService.getAll());
}
}
