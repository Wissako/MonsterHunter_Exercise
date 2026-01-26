package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HunterResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HunterMapper {

    HunterResponse toResponse(Hunter hunter);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huntingLogs", ignore = true)
    @Mapping(target = "mainWeapon", qualifiedByName = "mapWeapon")
    Hunter toEntity(HunterRequest request);

    @Named("mapWeapon")
    default HunterWeapons mapWeapon(String weapon) {
        try {
            return HunterWeapons.valueOf(weapon.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Arma no válida: " + weapon);
        }
    }
}