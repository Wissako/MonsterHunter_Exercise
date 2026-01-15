package com.luis.proyectofinal_luisalfonso.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "hunting_logs")
public class HuntingLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="hunting_date", nullable = false, length = 30)
    private LocalDateTime huntingDate;

    @Column(name="time_taken", nullable = false)
    private Integer timeTaken; // in minutes

    @Column(name="successful", nullable = false)
    private Boolean successful;
    //Relaciones
    @ManyToOne
    @JoinColumn(name = "hunter_id", nullable = false)
    private Hunter hunter;

    @ManyToOne
    @JoinColumn(name = "quest_id", nullable = false)
    private Quest quest;
}