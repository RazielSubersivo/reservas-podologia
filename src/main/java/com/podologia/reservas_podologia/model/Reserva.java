package com.podologia.reservas_podologia.model;



import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paciente;


    @ManyToOne
    private Profesional profesional;

    @OneToOne
    @JoinColumn(name = "hora_disponible_id")
    private HoraDisponible horaDisponible;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }


    public Profesional getProfesional() {
        return profesional;
    }

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public HoraDisponible getHoraDisponible() {
        return horaDisponible;
    }

    public void setHoraDisponible(HoraDisponible horaDisponible) {
        this.horaDisponible = horaDisponible;
    }
}