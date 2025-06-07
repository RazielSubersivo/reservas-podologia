package com.podologia.reservas_podologia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/reserva")
    public String mostrarReserva() {
        return "reserva";
    }

    @GetMapping("/profesional")
    public String mostrarProfesional() {
        return "profesional";
    }

    @GetMapping("/index")
    public String mostrarIndex() {
        return "index";
    }

    @GetMapping("/servicios")
    public String mostrarServicios() {
        return "servicios"; // Necesitarías un archivo servicios.html
    }

    @GetMapping("/quienes-somos")
    public String mostrarQuienesSomos() {
        return "quienes-somos"; // Necesitarías un archivo quienes-somos.html
    }
    // Eliminar: @GetMapping("/login")
    // public String mostrarLogin() {
    //     return "login";
    // }
}
