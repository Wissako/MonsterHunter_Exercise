package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.dto.request.QuestRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.QuestResponse;
import com.luis.proyectofinal_luisalfonso.service.QuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quests")
public class QuestController {

    @Autowired
    private QuestService questService;

    @PostMapping
    public ResponseEntity<QuestResponse> create(@RequestBody @Valid QuestRequest request) {
        return new ResponseEntity<>(questService.createQuest(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<QuestResponse>> getAll() {
        return ResponseEntity.ok(questService.getAllQuests());
    }
}