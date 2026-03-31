/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Usuario;
import com.milenita.repository.UsuarioRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/perfil")
    public String perfil(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElse(null);

        if (usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuario", usuario);
        return "perfil";
    }
}
