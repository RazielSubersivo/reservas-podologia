package com.podologia.reservas_podologia.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class HoraDisponible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    private LocalDateTime fechaHora;
    private boolean reservada = false;

    // Getters y Setters


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Profesional getProfesional() {
        return profesional;
    }
    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public boolean getReservada() {
        return reservada;
    }
    public void setReservada(boolean fechaHora) {
        this.reservada = reservada;
    }

    public boolean isReservada() {
        return reservada;

    }
}
