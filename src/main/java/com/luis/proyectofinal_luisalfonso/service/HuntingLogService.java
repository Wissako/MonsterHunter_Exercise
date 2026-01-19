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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HuntingLogService {
@Autowired
    private HuntingLogRepository huntingLogRepository;
@Autowired
    private HunterRepository hunterRepository;
@Autowired
    private QuestRepository questRepository;
@Autowired
        private HuntingLogMapper huntingLogMapper;


public HuntingLogResponse createLog(HuntingLogRequest request){
    Hunter hunter= hunterRepository.findById(request.hunterId())
            .orElseThrow(()-> new ResourceNotFoundException("Hunter not found", request.hunterId()));
    Quest quest= questRepository.findById(request.questId())
            .orElseThrow(()-> new ResourceNotFoundException("Quest not found", request.questId()));
    //Validación de Rango
    if(hunter.getRank() < quest.getDifficulty()){
        throw new IllegalArgumentException("Hunter rank is too low for this quest"+quest.getDifficulty()+" required, but hunter has rank "+hunter.getRank());
    }
    HuntingLog log = new HuntingLog();
    log.setHunter(hunter);
    log.setQuest(quest);
    log.setTimeTaken(request.timeTaken());
    log.setSuccessful(request.successful());
    log.setHuntingDate(LocalDateTime.now());

    HuntingLog savedLog = huntingLogRepository.save(log);
    return huntingLogMapper.toResponse(savedLog);
}
public List<HuntingLogResponse> getHistoryByHunter(Long hunterId){
    return huntingLogRepository.findByHunter_Id(hunterId)
            .stream()
            .map(huntingLogMapper::toResponse)
            .collect(Collectors.toList());
}
}
