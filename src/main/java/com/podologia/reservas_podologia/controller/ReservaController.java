package com.podologia.reservas_podologia.controller;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.model.Reserva;
import com.podologia.reservas_podologia.repository.HoraDisponibleRepository;
import com.podologia.reservas_podologia.repository.ReservaRepository;
import com.podologia.reservas_podologia.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {
    @Autowired
    private HoraDisponibleRepository horaDisponibleRepository;
    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/disponibles")
    public String mostrarHorasDisponibles(Model model) {
        List<HoraDisponible> horas = horaDisponibleRepository.findByProfesionalAndReservadaFalse(null); // Reemplaza con profesional autenticado
        model.addAttribute("horasDisponibles", horas);
        return "reservas";
    }

    @PostMapping("/reservar/{id}")
    public String reservarHora(@PathVariable Long id, @RequestParam String paciente) {
        HoraDisponible hora = horaDisponibleRepository.findById(id).orElseThrow();
        if (!hora.isReservada()) {
            hora.setReservada(true);
            Reserva reserva = new Reserva();
            reserva.setPaciente(paciente);
            reserva.setHoraDisponible(hora);
            reservaRepository.save(reserva);
        }
        return "redirect:/reservas/disponibles";
    }
}

