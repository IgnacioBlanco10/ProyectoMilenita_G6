/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.Rol;
import com.milenita.domain.Usuario;
import com.milenita.repository.RolRepository;
import com.milenita.repository.UsuarioRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USUARIO no encontrado"));

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        usuario.setRoles(Set.of(rolUsuario));

        if (usuario.getFotoPerfil() == null || usuario.getFotoPerfil().isBlank()) {
            usuario.setFotoPerfil("logo_milenita.jpeg");
        }
        
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    public Usuario obtenerPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    public void guardarAdmin(Usuario usuario) {
        Usuario usuarioExistente = null;

        if (usuario.getIdUsuario() != null) {
            usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
        }

        if (usuarioExistente != null) {
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(usuarioExistente.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
                usuario.setRoles(usuarioExistente.getRoles());
            }
        } else {
            if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new RuntimeException("El correo ya está registrado");
            }

            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                throw new RuntimeException("La contraseña es obligatoria");
            }

            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
                Rol rolUsuario = rolRepository.findByNombre("ROLE_USUARIO")
                        .orElseThrow(() -> new RuntimeException("Rol ROLE_USUARIO no encontrado"));
                usuario.setRoles(Set.of(rolUsuario));
            }
        }

        if (usuario.getActivo() == null) {
            usuario.setActivo(true);
        }

        usuarioRepository.save(usuario);
    }

    public void eliminarLogico(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        if (usuario != null) {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        }
    }

    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Set<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getPassword())
                .authorities(authorities)
                .disabled(!usuario.getActivo())
                .build();
    }
}