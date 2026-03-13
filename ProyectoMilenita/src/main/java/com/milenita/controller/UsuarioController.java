/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Rol;
import com.milenita.domain.Usuario;
import com.milenita.service.RolService;
import com.milenita.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping("/registro-form")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        Rol rolCliente = rolService.getRolPorNombre("CLIENTE");

        if (rolCliente == null) {
            rolCliente = new Rol();
            rolCliente.setNombre("CLIENTE");
            rolService.guardar(rolCliente);
            rolCliente = rolService.getRolPorNombre("CLIENTE");
        }

        usuario.setRol(rolCliente);
        usuarioService.guardar(usuario);

        return "redirect:/login";
    }
}
