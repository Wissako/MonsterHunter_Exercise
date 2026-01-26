package com.luis.proyectofinal_luisalfonso.mapper;

import com.luis.proyectofinal_luisalfonso.dto.request.QuestRequest;
import com.luis.proyectofinal_luisalfonso.dto.response.QuestResponse;
import com.luis.proyectofinal_luisalfonso.models.entities.Quest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestMapper {

    @Mapping(source = "target.name", target = "targetMonsterName")
    QuestResponse questToQuestResponse(Quest quest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "target", ignore = true)
    Quest toEntity(QuestRequest request);
}