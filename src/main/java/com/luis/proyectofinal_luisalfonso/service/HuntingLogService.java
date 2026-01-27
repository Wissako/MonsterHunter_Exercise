package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.dto.request.HuntingLogRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HuntingLogResponse;
import com.luis.proyectofinal_luisalfonso.error.ResourceNotFoundException;
import com.luis.proyectofinal_luisalfonso.mapper.HuntingLogMapper;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.models.entities.HuntingLog;
import com.luis.proyectofinal_luisalfonso.models.entities.Quest;
import com.luis.proyectofinal_luisalfonso.repositories.HunterRepository;
import com.luis.proyectofinal_luisalfonso.repositories.HuntingLogRepository;
import com.luis.proyectofinal_luisalfonso.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HuntingLogService {

    @Autowired
    private HuntingLogRepository huntingLogRepository;
    @Autowired
    private HunterRepository hunterRepository;
    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private HuntingLogMapper huntingLogMapper;

    @Transactional
    public HuntingLogResponse createLog(HuntingLogRequest request) {
        Hunter hunter = hunterRepository.findById(request.hunterId())
                .orElseThrow(() -> new ResourceNotFoundException("Cazador", request.hunterId()));
        Quest quest = questRepository.findById(request.questId())
                .orElseThrow(() -> new ResourceNotFoundException("Misión", request.questId()));

        //Lógica de Negocio (Rango)
        if (hunter.getRank() < quest.getDifficulty()) {
            throw new IllegalArgumentException("Rango insuficiente: Se requiere " + quest.getDifficulty() + " pero tienes " + hunter.getRank());
        }

        HuntingLog log = huntingLogMapper.toEntity(request);

        log.setHunter(hunter);
        log.setQuest(quest);
        log.setHuntingDate(LocalDateTime.now());

        return huntingLogMapper.toResponse(huntingLogRepository.save(log));
    }

    public List<HuntingLogResponse> getHistoryByHunter(Long hunterId) {
        return huntingLogRepository.findByHunter_Id(hunterId).stream()
                .map(huntingLogMapper::toResponse)
                .collect(Collectors.toList());
    }
}