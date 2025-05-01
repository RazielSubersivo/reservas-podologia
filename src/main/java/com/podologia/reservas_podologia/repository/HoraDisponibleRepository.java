package com.podologia.reservas_podologia.repository;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.model.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoraDisponibleRepository extends JpaRepository<HoraDisponible, Long> {
    List<HoraDisponible> findByProfesionalAndReservadaFalse(Profesional profesional);
}