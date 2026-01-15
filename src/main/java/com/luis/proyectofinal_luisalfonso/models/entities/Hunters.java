package com.luis.proyectofinal_luisalfonso.models.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hunters")
public class Hunters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="hunter_name", nullable = false, length = 25)
    private String name;

    @Column(name="hunter_rank", nullable = false)
    private Integer rank;

    @Column(name="main_weapon", nullable = false, length = 20)
    private String mainWeapon;

    @Column(name="email", nullable = false)
    private String email;
}