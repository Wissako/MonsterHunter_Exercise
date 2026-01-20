package com.luis.proyectofinal_luisalfonso.service;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import com.luis.proyectofinal_luisalfonso.repositories.HunterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HunterService {
    @Autowired
    private HunterRepository hunterRepository;

    //crear cazador
    public Hunter createHunter(HunterRequest request){
      if(hunterRepository.findByEmail(request.email())){
          throw new IllegalArgumentException("El email ya está en uso");
      }
        Hunter hunter = new Hunter();
        hunter.setName(request.name());
        hunter.setRank(request.rank());
        hunter.setEmail(request.email());
        //convertir String a enum
        try{
            hunter.setMainWeapon(HunterWeapons.valueOf(request.mainWeapon().toUpperCase()));
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Tipo de arma no válido: ejm=(GREAT_SWORD,BOW,HAMMER)" + request.mainWeapon());
        }
        return hunterRepository.save(hunter);
    }
    // Listar todos
    public List<Hunter> getAllHunters() {
        return hunterRepository.findAll();
    }

    // Buscar por ID
    public Hunter getHunterById(Long id) {
        return hunterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cazador no encontrado"));
    }

    // Eliminar cazador
    public void deleteHunter(Long id) {
        if (!hunterRepository.existsById(id)) {
            throw new RuntimeException("Cazador no encontrado");
        }
        hunterRepository.deleteById(id);
    }

}
