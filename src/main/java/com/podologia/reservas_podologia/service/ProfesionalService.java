package com.podologia.reservas_podologia.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.model.SeguridadConfig;
import com.podologia.reservas_podologia.repository.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import java.util.Optional;

@Service
public class ProfesionalService {
    @Autowired
    private PasswordEncoder passwordEncoder;
// Inyectamos el bean que definiste en SeguridadConfig

    public void registrarProfesional(String nombre, String especialidad, String passwordPlano) {
        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setEspecialidad(especialidad);
        profesional.setPassword(passwordEncoder.encode(passwordPlano)); // Se encripta correctamente
        // Guardar en el repositorio...
    }

    public boolean verificarPassword(String passwordPlano, String passwordEncriptado) {
        return passwordEncoder.matches(passwordPlano, passwordEncriptado); // Compara bien
    }
}