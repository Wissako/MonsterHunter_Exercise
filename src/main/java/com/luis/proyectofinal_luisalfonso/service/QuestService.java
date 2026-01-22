package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.dto.request.QuestRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.QuestResponse;
import com.luis.proyectofinal_luisalfonso.error.ResourceNotFoundException;
import com.luis.proyectofinal_luisalfonso.mapper.QuestMapper;
import com.luis.proyectofinal_luisalfonso.models.entities.Monster;
import com.luis.proyectofinal_luisalfonso.models.entities.Quest;
import com.luis.proyectofinal_luisalfonso.repositories.MonsterRepository;
import com.luis.proyectofinal_luisalfonso.repositories.QuestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestService {
    @Autowired
        private QuestMapper questMapper;
    @Autowired
        private QuestRepository questRepository;
    @Autowired
    private MonsterRepository monsterRepository;

    public QuestResponse createQuest(@Valid QuestRequest request){
        Monster target= monsterRepository.findById(request.targetMonsterId())
                .orElseThrow(()-> new ResourceNotFoundException("Monster not found", request.targetMonsterId()));
    //crear quest
        Quest quest = new Quest();
        quest.setName(request.name());
        quest.setDifficulty(request.difficulty());
        quest.setReward(request.reward());
        quest.setTarget(target);
        //guardar quest
        Quest savedQuest = questRepository.save(quest);
        return questMapper.questToQuestResponse(savedQuest);
    }
    public List<QuestResponse> getAllQuests() {
        return questRepository.findAll()
                .stream()
                .map(questMapper::questToQuestResponse)
                .collect(Collectors.toList());
    }

}
