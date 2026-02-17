package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.dto.request.MonsterRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.MonsterResponse;
import com.luis.proyectofinal_luisalfonso.models.enums.HabitatName;
import com.luis.proyectofinal_luisalfonso.service.MonsterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monsters")

public class MonsterController {
    @Autowired
    private MonsterService monsterService;

    // /monsters
    @GetMapping
    public ResponseEntity<List<MonsterResponse>> getAll() {
        return ResponseEntity.ok(monsterService.getAllMonsters());
    }

    // /monsters/search?name=(Rathalos)
    @GetMapping("/search")
    public ResponseEntity<List<MonsterResponse>> search(@RequestParam String name) {
        return ResponseEntity.ok(monsterService.searchByName(name));
    }

    // /monsters/weakness/(weakness)
    @GetMapping("weakness/{weakness}")
    public ResponseEntity<List<MonsterResponse>> getByWeakness(@PathVariable String weakness) {
        return ResponseEntity.ok(monsterService.filterByWeakness(weakness));
    }
    @PostMapping
    public ResponseEntity<MonsterResponse> create(@RequestBody MonsterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monsterService.createMonster(request));
    }

    @GetMapping("/habitat/{zone}")
    public ResponseEntity<List<MonsterResponse>> getByHabitat(@PathVariable HabitatName zone) {
        return ResponseEntity.ok(monsterService.searchByHabitat(zone));
    }

    @GetMapping("/complete-search")
    public ResponseEntity<List<MonsterResponse>> completeSearch(
            @RequestParam String name,
            @RequestParam String weakness,
            @RequestParam HabitatName zone) {
        return ResponseEntity.ok(monsterService.complexSearch(name, weakness, zone));
    }

    //Monstruos paginados
    @GetMapping("/paged")
    public ResponseEntity<Page<MonsterResponse>> getAllPaged(
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        return ResponseEntity.ok(monsterService.getAllMonstersPaged(pageable));
    }
}