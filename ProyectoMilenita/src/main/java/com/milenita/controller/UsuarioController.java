/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Usuario;
import com.milenita.repository.UsuarioRepository;
import com.milenita.service.PedidoService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/perfil")
    public String perfil(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElse(null);

        if (usuario == null) {
            return "redirect:/";
        }

        usuario.setPassword("");
        model.addAttribute("usuario", usuario);
        model.addAttribute("pedidos", pedidoService.listarPorUsuario(usuario.getIdUsuario()));
        
        return "perfil";
    }

    @PostMapping("/perfil/guardar")
    public String guardarPerfil(@ModelAttribute Usuario usuarioForm, Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElse(null);

        if (usuario == null) {
            return "redirect:/";
        }

        usuario.setNombre(usuarioForm.getNombre());
        usuario.setCorreo(usuarioForm.getCorreo());
        usuario.setFotoPerfil(usuarioForm.getFotoPerfil());
        usuario.setGenero(usuarioForm.getGenero());

        if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(usuarioForm.getPassword()));
        }

        usuarioRepository.save(usuario);

        model.addAttribute("usuario", usuario);
        model.addAttribute("exito", "Perfil actualizado correctamente");
        return "perfil";
    }
}
