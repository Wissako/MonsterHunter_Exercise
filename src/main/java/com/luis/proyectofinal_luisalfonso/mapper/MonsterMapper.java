package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.response.MonsterResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Habitat;
import com.luis.proyectofinal_luisalfonso.models.entities.Material;
import com.luis.proyectofinal_luisalfonso.models.entities.Monster;
import org.mapstruct.*;

import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface MonsterMapper {
    //mapeamos la lista drops a materials
    @Mapping(source = "drops",target = "materials",qualifiedByName = "mapMaterials")
    //mapeamos la lista habitats a habitats
    @Mapping(source = "habitats",target = "habitats",qualifiedByName = "mapHabitats")
    MonsterResponse toResponse(Monster monster);
    //convertir objetos a nombres
    @Named("mapMaterials")
    default java.util.List<String> mapMaterials(java.util.List<Material> materials) {
        if(materials == null){
            return null;
        }
        return materials.stream()
                .map(Material::getName)
                .collect(Collectors.toList());
    }
    @Named("mapHabitats")
    default java.util.List<String> mapHabitats(java.util.List<Habitat> habitats){
        if(habitats == null){
            return null;
        }
        return habitats.stream()
                .map(h->h.getZone().name())
                .collect(Collectors.toList());
    }
}