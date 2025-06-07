package com.podologia.reservas_podologia.controller;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.service.ProfesionalService;
import com.podologia.reservas_podologia.repository.HoraDisponibleRepository;
import com.podologia.reservas_podologia.repository.ProfesionalRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importar RedirectAttributes

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/profesional")
public class ProfesionalController {

    @Autowired
    private HoraDisponibleRepository horaDisponibleRepository;

    @Autowired
    private ProfesionalService profesionalService;

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @PostMapping("/agregar-hora")
    public String agregarHora(@RequestParam LocalDateTime fechaHora, Authentication authentication) {
        // ... (Tu lógica existente para agregar hora)
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Profesional profesional = profesionalRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        HoraDisponible horaDisponible = new HoraDisponible();
        horaDisponible.setFechaHora(fechaHora);
        horaDisponible.setProfesional(profesional);
        horaDisponibleRepository.save(horaDisponible);

        return "redirect:/profesional/mantenedor";
    }

    @GetMapping("/mantenedor")
    public String mostrarMantenedor(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Profesional profesional = profesionalRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));
        List<HoraDisponible> horasDisponibles = horaDisponibleRepository.findByProfesionalAndReservadaFalse(profesional);
        model.addAttribute("horasDisponibles", horasDisponibles);
        return "mantenedor_profesional";
    }

    @PostMapping("/registrar")
    public String registrarProfesional(
            @RequestParam String nombre,
            @RequestParam String especialidad,
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) { // Usar RedirectAttributes para mensajes después de la redirección
        try {
            profesionalService.registrarProfesional(nombre, especialidad, email, password);
            redirectAttributes.addFlashAttribute("mensajeExito", "Profesional registrado exitosamente. Por favor, inicia sesión.");
            return "redirect:/login"; // Redirige al login
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al registrar profesional: " + e.getMessage());
            return "redirect:/profesional"; // Redirige de vuelta a la página de registro con un mensaje de error
        }
    }
}