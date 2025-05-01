package com.podologia.reservas_podologia.repository;

import com.podologia.reservas_podologia.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {
    Optional<Profesional> findByEmail(String email);
    Optional<Profesional> findByNombre(String nombre);
}
