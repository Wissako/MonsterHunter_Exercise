package com.luis.proyectofinal_luisalfonso.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonsterRequest {
    private String name;
    private String type;
    private String element;
    private String weakness;

    @Range(min = 1, max = 10)
    private Integer threatLevel;

    private String imageUrl;
}