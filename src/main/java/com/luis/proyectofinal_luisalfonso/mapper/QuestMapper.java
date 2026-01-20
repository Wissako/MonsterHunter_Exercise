package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.response.QuestResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Quest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestMapper {
    @Mapping(source = "target.name", target = "targetMonsterName")
    QuestResponse questToQuestResponse(Quest quest);


}
