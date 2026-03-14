/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.config;

import com.milenita.domain.Rol;
import com.milenita.domain.Usuario;
import com.milenita.repository.RolRepository;
import com.milenita.repository.UsuarioRepository;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Admin {

    @Bean
    CommandLineRunner crearAdmin(
            UsuarioRepository usuarioRepository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol(null, "ROLE_ADMIN")));

            rolRepository.findByNombre("ROLE_USUARIO")
                    .orElseGet(() -> rolRepository.save(new Rol(null, "ROLE_USUARIO")));

            if (usuarioRepository.findByCorreo("admin@milenita.com").isEmpty()) {

                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setCorreo("admin@milenita.com");
                admin.setPassword(passwordEncoder.encode("Admin123"));
                admin.setActivo(true);
                admin.setRoles(Set.of(rolAdmin));

                usuarioRepository.save(admin);
            }
        };
    }
}
