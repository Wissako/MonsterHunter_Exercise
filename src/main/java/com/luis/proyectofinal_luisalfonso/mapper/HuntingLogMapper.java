package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.request.HuntingLogRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.HuntingLogResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.HuntingLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HuntingLogMapper {

    @Mapping(source = "hunter.name", target = "hunterName")
    @Mapping(source = "quest.name", target = "questName")
    @Mapping(source = "quest.target.name", target = "monsterName")
    HuntingLogResponse toResponse(HuntingLog huntingLog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hunter", ignore = true)
    @Mapping(target = "quest", ignore = true)
    @Mapping(target = "huntingDate", ignore = true)
    HuntingLog toEntity(HuntingLogRequest request);
}