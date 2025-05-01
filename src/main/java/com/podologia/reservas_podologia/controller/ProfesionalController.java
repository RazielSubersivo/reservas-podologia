package com.podologia.reservas_podologia.controller;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.repository.HoraDisponibleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {
    @Autowired
    private HoraDisponibleRepository horaDisponibleRepository;

    @PostMapping("/agregar-hora")
    public String agregarHora(@RequestParam LocalDateTime fechaHora, Authentication authentication) {
        Profesional profesional = (Profesional) authentication.getPrincipal();
        HoraDisponible hora = new HoraDisponible();
        hora.setFechaHora(fechaHora);
        hora.setProfesional(profesional);
        horaDisponibleRepository.save(hora);
        return "redirect:/profesional/mantenedor";
    }
}
