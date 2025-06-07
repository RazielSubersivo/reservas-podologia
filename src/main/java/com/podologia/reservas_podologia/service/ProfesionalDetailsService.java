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
    public  UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profesional profesional = profesionalRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profesional no encontrado"));

        return User.builder()
                .username(profesional.getEmail())
                .password(profesional.getPassword()) // Asume que ya está encriptada
                .roles("PROFESIONAL") // O los roles que necesites
                .build();
    }
}