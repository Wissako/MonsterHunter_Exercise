package com.luis.proyectofinal_luisalfonso.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "monsters")
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="monster_name", nullable = false, length = 30)
    private String name;

    @Column(name="monster_type", nullable = false, length = 20)
    private String type;

    @Column(name="monster_element",length=15)
    private String element;

    @Column(name="monster_weakness",length=30)
    private String weakness;

    @Column(name="monster_Threat_level", nullable = false)
    @Min(0)
    @Max(10)
    private Integer threatLevel=5;
    // Relaciones
    @ManyToMany
    @JoinTable(
            name = "monster_materials",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> drops;

    @ManyToMany
    @JoinTable(
            name = "monster_habitats",
            joinColumns = @JoinColumn(name = "monster_id"),
            inverseJoinColumns = @JoinColumn(name = "habitat_id")
    )
    private List<Habitat> habitats;
}