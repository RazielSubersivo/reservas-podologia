package com.podologia.reservas_podologia.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.repository.ProfesionalRepository; //  Asegúrate de importar el repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfesionalService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfesionalRepository profesionalRepository;  //  Inyecta el repositorio

    public void registrarProfesional(String nombre, String especialidad, String email, String passwordPlano) {
        Profesional profesional = new Profesional();
        profesional.setNombre(nombre);
        profesional.setEspecialidad(especialidad);
        profesional.setEmail(email);
        profesional.setPassword(passwordEncoder.encode(passwordPlano));
        profesionalRepository.save(profesional);  //  Guarda usando el repositorio
    }

    public boolean verificarPassword(String passwordPlano, String passwordEncriptado) {
        return passwordEncoder.matches(passwordPlano, passwordEncriptado);
    }
}