package com.luis.proyectofinal_luisalfonso.models.entities;

import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hunters")
public class Hunter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="hunter_name", nullable = false, length = 25)
    private String name;

    @Column(name="hunter_rank", nullable = false)
    private Integer rank;

    @Column(name="main_weapon", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private HunterWeapons mainWeapon=HunterWeapons.GREAT_SWORD;

    @Column(name="email", nullable = false)
    private String email;
}