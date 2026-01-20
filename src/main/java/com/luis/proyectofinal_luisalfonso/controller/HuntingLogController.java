package com.luis.proyectofinal_luisalfonso.controller;

import com.luis.proyectofinal_luisalfonso.dto.request.HuntingLogRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HuntingLogResponse;
import com.luis.proyectofinal_luisalfonso.service.HuntingLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hunting-logs")
public class HuntingLogController {
@Autowired
        private HuntingLogService huntingLogService;

@PostMapping
    public ResponseEntity<HuntingLogResponse> create(@RequestBody @Valid HuntingLogRequest request){
        HuntingLogResponse response = huntingLogService.createLog(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
}
@GetMapping("/hunter/{hunterId}")
    public ResponseEntity<List<HuntingLogResponse>> getHistory(@PathVariable Long hunterId){
    List<HuntingLogResponse> history = huntingLogService.getHistoryByHunter(hunterId);
    return ResponseEntity.ok(history);
}
}
