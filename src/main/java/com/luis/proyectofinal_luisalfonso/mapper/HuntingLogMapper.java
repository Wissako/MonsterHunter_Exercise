package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.response.HuntingLogResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.HuntingLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HuntingLogMapper {

    //Mapeo manual para sacar los nombres

    // Del objeto 'hunter', coge el campo 'name' y ponlo en 'hunterName' del DTO
    @Mapping(source = "hunter.name", target = "hunterName")

    // Del objeto 'quest', coge el campo 'name' y ponlo en 'questName'
    @Mapping(source = "quest.name", target = "questName")

    // Del objeto 'quest', entra en 'target' (Monstruo) y coge su 'name'
    @Mapping(source = "quest.target.name", target = "monsterName")

    HuntingLogResponse toResponse(HuntingLog huntingLog);

    /* NOTA: No necesitamos 'toEntity' aquí porque en el Service creamos
     la entidad manualmente para buscar los objetos Hunter y Quest por ID.
     MapStruct por defecto no sabe convertir un "Long ID" a un objeto "Hunter".
    */
}