package com.podologia.reservas_podologia.service;
import com.podologia.reservas_podologia.model.Profesional;
import com.podologia.reservas_podologia.repository.ProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProfesionalDetailsService implements UserDetailsService {

    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profesional profesional = profesionalRepository.findByNombre(username)
                .orElseThrow(() -> new UsernameNotFoundException("Profesional no encontrado"));

        return new User(
                profesional.getNombre(), // El campo que usas como username
                profesional.getPassword(), // Contraseña encriptada (con BCrypt)
                Collections.emptyList() // Roles o authorities si los tienes
        );
    }
}