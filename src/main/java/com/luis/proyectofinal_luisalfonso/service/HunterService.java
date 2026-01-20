package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HunterResponse;
import com.luis.proyectofinal_luisalfonso.mapper.HunterMapper;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import com.luis.proyectofinal_luisalfonso.repositories.HunterRepository;
import com.luis.proyectofinal_luisalfonso.error.ResourceNotFoundException; // Asegúrate de importar tu excepción
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HunterService {

    @Autowired
    private HunterRepository hunterRepository;

    @Autowired
    private HunterMapper hunterMapper;

    // CREAR
    public HunterResponse createHunter(HunterRequest request) {
        if (hunterRepository.findByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }

        Hunter hunter = new Hunter();
        hunter.setName(request.name());
        hunter.setRank(request.rank());
        hunter.setEmail(request.email());

        try {
            hunter.setMainWeapon(HunterWeapons.valueOf(request.mainWeapon().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de arma no válido: " + request.mainWeapon());
        }

        Hunter savedHunter = hunterRepository.save(hunter);

        //Usamos el mapper para devolver DTO
        return hunterMapper.toResponse(savedHunter);
    }

    // LISTAR TODOS
    public List<HunterResponse> getAllHunters() {
        return hunterRepository.findAll()
                .stream()
                .map(hunterMapper::toResponse)
                .collect(Collectors.toList());
    }

    // BUSCAR POR ID
    public HunterResponse getHunterById(Long id) {
        Hunter hunter = hunterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cazador", id));
        return hunterMapper.toResponse(hunter);
    }

    // ELIMINAR POR ID
    public void deleteHunter(Long id) {
        if (!hunterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cazador", id);
        }
        hunterRepository.deleteById(id);
    }
}