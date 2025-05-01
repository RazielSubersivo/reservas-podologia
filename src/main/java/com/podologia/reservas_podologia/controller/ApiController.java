package com.podologia.reservas_podologia.controller;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.repository.HoraDisponibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private HoraDisponibleRepository horaDisponibleRepository;

    @GetMapping("/horas-disponibles")
    public List<Map<String, Object>> obtenerHorasDisponibles() {
        List<HoraDisponible> horas = horaDisponibleRepository.findByProfesionalAndReservadaFalse(null); // Agregar autenticación
        return horas.stream().map(h -> Map.of(
                "title", (Object) h.getProfesional().getNombre(),
                "start", (Object) h.getFechaHora().toString()
        )).collect(Collectors.toList());
    }
}