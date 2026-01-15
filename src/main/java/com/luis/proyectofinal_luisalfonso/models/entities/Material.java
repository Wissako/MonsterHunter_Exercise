package com.luis.proyectofinal_luisalfonso.models.entities;

import com.luis.proyectofinal_luisalfonso.models.enums.MaterialRarity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="material_name", nullable = false, length = 30)
    private String name;

    @Column(name="material_value", nullable = false)
    private double value;

    @Column(name="material_rarity", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MaterialRarity rarity=MaterialRarity.COMMON;
}
