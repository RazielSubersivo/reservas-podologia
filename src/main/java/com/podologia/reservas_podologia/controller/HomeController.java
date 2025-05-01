package com.podologia.reservas_podologia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/reserva")
    public String mostrarReserva() {
        return "reserva"; // Spring buscará "reserva.html" en /templates/
    }

    @GetMapping("/profesional")
    public String mostrarProfesional() {
        return "profesional"; // Spring buscará "profesional.html" en /templates/
    }
    @GetMapping("/index")
    public String mostrarIndex() {
        return "index"; // Spring buscará "index.html" en /templates/
    }

}

