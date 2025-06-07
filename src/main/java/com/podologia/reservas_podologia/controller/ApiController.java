package com.podologia.reservas_podologia.controller;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.repository.HoraDisponibleRepository;
import com.podologia.reservas_podologia.repository.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @GetMapping("/horas-disponibles")
    public List<Map<String, Object>> obtenerHorasDisponibles(Authentication authentication) {
        String email;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        Profesional profesional = profesionalRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        List<HoraDisponible> horas = horaDisponibleRepository.findByProfesionalAndReservadaFalse(profesional);
        return horas.stream().map(h -> Map.of(
                "id", h.getId(), // **Añadir el ID aquí**
                "title", (Object) h.getProfesional().getNombre(),
                "start", h.getFechaHora().toString()
        )).collect(Collectors.toList());
    }
}