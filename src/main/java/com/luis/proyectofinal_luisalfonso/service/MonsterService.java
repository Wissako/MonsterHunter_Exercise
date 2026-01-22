package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.dto.response.MonsterResponse;
import com.luis.proyectofinal_luisalfonso.mapper.MonsterMapper;
import com.luis.proyectofinal_luisalfonso.models.enums.HabitatName;
import com.luis.proyectofinal_luisalfonso.repositories.MonsterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MonsterService {
    @Autowired
    private MonsterRepository monsterRepository;
    @Autowired
    private MonsterMapper monsterMapper;

    //Listar todos los monstruos
    public List<MonsterResponse> getAllMonsters() {
        return monsterRepository.findAll()
                .stream()
                .map(monsterMapper::toResponse)
                .collect(Collectors.toList());
    }
    //Buscar monstruos por nombre (busqueda parcial)
    public List<MonsterResponse> searchByName(String name){
        return monsterRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(monsterMapper::toResponse)
                .collect(Collectors.toList());
    }
    //Filtrar monstruos por debilidad
    public List<MonsterResponse> filterByWeakness(String weakness){
        return monsterRepository.findByWeakness(weakness)
                .stream()
                .map(monsterMapper::toResponse)
                .collect(Collectors.toList());
    }
    //Buscar por Hábitat (Consulta con JOIN automático)
    public List<MonsterResponse> searchByHabitat(HabitatName zone){
        return monsterRepository.findByHabitats_Zone(zone)
                .stream()
                .map(monsterMapper::toResponse)
                .collect(Collectors.toList());
    }
 //Buscar por nombre, debilidad y hábitat (Consulta compleja con JOIN FETCH y LOWER CONCAT)
    public List<MonsterResponse> complexSearch(String name,String weakness, HabitatName zone){
        return monsterRepository.findComplexSearch(name, weakness, zone)
                .stream()
                .map(monsterMapper::toResponse)
                .collect(Collectors.toList());
    }
}

