package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HunterResponse;
import com.luis.proyectofinal_luisalfonso.error.ResourceNotFoundException;
import com.luis.proyectofinal_luisalfonso.mapper.HunterMapper;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.repositories.HunterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class HunterService {

    @Autowired
    private HunterRepository hunterRepository;
    @Autowired
    private HunterMapper hunterMapper;

    @Transactional
    public HunterResponse createHunter(HunterRequest request) {
        // 1. Validaciones de Negocio
        if (hunterRepository.findByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
        // 2. MapStruct crea la entidad
        Hunter hunter = hunterMapper.toEntity(request);
        //guardar cazador
        return hunterMapper.toResponse(hunterRepository.save(hunter));
    }

    public List<HunterResponse> getAllHunters() {
        return hunterRepository.findAll().stream()
                .map(hunterMapper::toResponse)
                .collect(Collectors.toList());
    }

    public HunterResponse getHunterById(Long id) {
        Hunter hunter = hunterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cazador", id));
        return hunterMapper.toResponse(hunter);
    }

    @Transactional
    public void deleteHunter(Long id) {
        if (!hunterRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cazador", id);
        }
        hunterRepository.deleteById(id);
    }
}