package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.response.HuntingLogResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.HuntingLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HuntingLogMapper {


    @Mapping(source = "hunter.name", target = "hunterName")


    @Mapping(source = "quest.name", target = "questName")


    @Mapping(source = "quest.target.name", target = "monsterName")

    HuntingLogResponse toResponse(HuntingLog huntingLog);


}