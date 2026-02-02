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
        if (hunterRepository.findByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
        Hunter hunter = hunterMapper.toEntity(request);
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

    //modificar cazador
    @Transactional
    public HunterResponse updateHunter(Long id, HunterRequest request) {
        // 1. Buscamos al cazador antiguo
        Hunter existingHunter = hunterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cazador", id));

        // 2. Comprobamos si el email cambia y si ya existe en otro usuario
        if (!existingHunter.getEmail().equalsIgnoreCase(request.email()) &&
                hunterRepository.findByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está en uso por otro cazador");
        }

        // 3. Actualizamos los datos (usando el mapper o setters manuales)
        // actualizar a mano aquí para no perder el ID
        existingHunter.setName(request.name());
        existingHunter.setRank(request.rank());
        existingHunter.setEmail(request.email());
        existingHunter.setMainWeapon(hunterMapper.mapWeapon(request.mainWeapon())); // Reusamos el método del mapper para convertir String a Enum

        // 4. Guardamos
        return hunterMapper.toResponse(hunterRepository.save(existingHunter));
    }
}