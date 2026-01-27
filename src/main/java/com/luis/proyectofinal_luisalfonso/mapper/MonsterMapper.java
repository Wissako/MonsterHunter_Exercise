package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.response.MonsterResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Habitat;
import com.luis.proyectofinal_luisalfonso.models.entities.Material;
import com.luis.proyectofinal_luisalfonso.models.entities.Monster;
import org.mapstruct.*;

import java.util.Set; // Importante
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MonsterMapper {

    @Mapping(source = "drops", target = "materials", qualifiedByName = "mapMaterials")
    @Mapping(source = "habitats", target = "habitats", qualifiedByName = "mapHabitats")
    MonsterResponse toResponse(Monster monster);

    // Recibe SET, devuelve LIST
    @Named("mapMaterials")
    default java.util.List<String> mapMaterials(Set<Material> materials) {
        if(materials == null) return null;
        return materials.stream()
                .map(Material::getName)
                .collect(Collectors.toList());
    }

    // Recibe SET, devuelve LIST
    @Named("mapHabitats")
    default java.util.List<String> mapHabitats(Set<Habitat> habitats){
        if(habitats == null) return null;
        return habitats.stream()
                .map(h -> h.getZone().name())
                .collect(Collectors.toList());
    }
}