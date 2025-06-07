package com.podologia.reservas_podologia.controller;

import com.podologia.reservas_podologia.model.HoraDisponible;
import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.model.Reserva;
import com.podologia.reservas_podologia.repository.HoraDisponibleRepository;
import com.podologia.reservas_podologia.repository.ProfesionalRepository;
import com.podologia.reservas_podologia.repository.ReservaRepository;
import com.podologia.reservas_podologia.service.ReservaService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails; // Importar UserDetails
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Importar RedirectAttributes

import java.util.List;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private HoraDisponibleRepository horaDisponibleRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaService reservaService;

    @GetMapping("/disponibles")
    public String mostrarHorasDisponibles(Model model, Authentication authentication) {
        // Asegurarse de que el principal sea un UserDetails para obtener el email
        String email;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString(); // Fallback o lanzar excepción si es un tipo inesperado
        }

        // Buscar el profesional por email si es necesario, o pasar el objeto Profesional si ya lo tienes
        // (Asumiendo que el profesional se obtiene del principal en un contexto real de Spring Security)
        // Por ahora, para que compile y funcione con el flujo actual, si 'authentication.getPrincipal()' ya devuelve un Profesional
        // Asegúrate de que tu ProfesionalDetailsService lo configure correctamente.
        // Si no, necesitarías inyectar ProfesionalRepository aquí también para buscarlo.
        // Para simplificar, asumiremos que el principal ya es un Profesional o puedes buscarlo por email.
        // profesionalRepository inyectado para buscar
        // @Autowired
        // private ProfesionalRepository profesionalRepository;
        // Profesional profesional = profesionalRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        // Temporalmente, si el principal NO es un Profesional directamente, esta línea podría fallar.
        // Para que funcione con ProfesionalDetailsService, el principal debería ser un UserDetails
        // y luego buscar el profesional.
        // Profesional profesional = (Profesional) authentication.getPrincipal(); // Esto solo funcionará si tu AuthenticationProvider pone directamente el objeto Profesional como principal.
        // La forma más robusta es buscar el profesional a partir del email del UserDetails.
        Profesional profesional = profesionalRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado para el email: " + email));

        List<HoraDisponible> horas = horaDisponibleRepository.findByProfesionalAndReservadaFalse(profesional);
        model.addAttribute("horasDisponibles", horas);
        return "reserva";
    }

    @PostMapping("/reservar/{horaId}")
    public String reservarHora(
            @PathVariable Long horaId,
            @RequestParam String paciente,
            Authentication authentication,
            RedirectAttributes redirectAttributes) { // Añadir RedirectAttributes
        String email;
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        Profesional profesional = profesionalRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

        HoraDisponible hora = horaDisponibleRepository.findById(horaId)
                .orElseThrow(() -> new RuntimeException("Hora no encontrada"));

        if (!hora.isReservada()) {
            hora.setReservada(true);
            horaDisponibleRepository.save(hora);

            Reserva reserva = new Reserva();
            reserva.setPaciente(paciente);
            reserva.setHoraDisponible(hora);
            reserva.setProfesional(profesional);
            reservaService.crearReserva(reserva);

            redirectAttributes.addFlashAttribute("mensajeExitoReserva", "Hora reservada exitosamente para " + paciente + ".");
            return "redirect:/reservas/disponibles"; // Redirige a la página principal de reservas
        } else {
            redirectAttributes.addFlashAttribute("mensajeErrorReserva", "La hora seleccionada ya ha sido reservada.");
            return "redirect:/reservas/disponibles?error=hora_ocupada"; // Redirige con mensaje de error
        }
    }
    // Necesitarás el ProfesionalRepository aquí para buscar el profesional por email
    @Autowired
    private ProfesionalRepository profesionalRepository;
}
