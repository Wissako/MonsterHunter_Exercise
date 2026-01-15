package com.luis.proyectofinal_luisalfonso.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quest")
public class Quests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="quest_name", nullable = false, length = 50)
    private String name;

    @Column(name="quest_difficulty", nullable = false, length = 20)
    private Integer difficulty;

    @Column(name="quest_reward", nullable = false)
    private double reward;

}