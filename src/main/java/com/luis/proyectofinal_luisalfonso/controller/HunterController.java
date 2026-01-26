package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HunterResponse;
import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import com.luis.proyectofinal_luisalfonso.service.HunterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/hunters")
public class HunterController {

    @Autowired
    private HunterService hunterService;

    // POST: Crear Cazador
    @PostMapping
    public ResponseEntity<HunterResponse> create(@RequestBody @Valid HunterRequest request) {
        return new ResponseEntity<>(hunterService.createHunter(request), HttpStatus.CREATED);
    }

    // GET: Listar todos
    @GetMapping
    public ResponseEntity<List<HunterResponse>> getAll() {
        return ResponseEntity.ok(hunterService.getAllHunters());
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<HunterResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hunterService.getHunterById(id));
    }

    // DELETE: Borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hunterService.deleteHunter(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/weapons")
    public ResponseEntity<List<String>> getWeapons() {
        // Convertimos el Enum a una lista de Strings
        List<String> weapons = Arrays.stream(HunterWeapons.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(weapons);
    }
}