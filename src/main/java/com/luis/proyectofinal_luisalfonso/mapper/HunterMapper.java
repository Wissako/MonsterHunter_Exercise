package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.request.HunterRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HunterResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Hunter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HunterMapper {

    // De Entidad a Respuesta
    HunterResponse toResponse(Hunter hunter);

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "huntingLogs", ignore = true)
    Hunter toEntity(HunterRequest request);
}