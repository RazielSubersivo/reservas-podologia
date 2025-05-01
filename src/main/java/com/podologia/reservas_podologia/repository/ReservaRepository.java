package com.podologia.reservas_podologia.repository;


import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.model.Reserva;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}



