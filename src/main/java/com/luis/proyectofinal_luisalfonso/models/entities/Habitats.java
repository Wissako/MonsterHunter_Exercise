package com.luis.proyectofinal_luisalfonso.models.entities;

import com.luis.proyectofinal_luisalfonso.models.enums.HabitatName;
import com.luis.proyectofinal_luisalfonso.models.enums.HabitatWeather;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "habitats")
public class Habitats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private HabitatName zone=HabitatName.ANCIENT_FOREST;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private HabitatWeather weather=HabitatWeather.SUNNY;


}
