/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Usuario;
import com.milenita.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author nacho
 */
@Controller
public class AdminUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "usuarios";
    }

    @GetMapping("/admin/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        Usuario usuario = new Usuario();
        usuario.setActivo(true);

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", usuarioService.listarRoles());

        return "usuario_form";
    }

    @GetMapping("/admin/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long idUsuario, Model model) {
        Usuario usuario = usuarioService.obtenerPorId(idUsuario);

        if (usuario == null) {
            return "redirect:/admin/usuarios";
        }

        usuario.setPassword("");

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", usuarioService.listarRoles());

        return "usuario_form";
    }

    @PostMapping("/admin/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.guardarAdmin(usuario);
            return "redirect:/admin/usuarios";
        } catch (RuntimeException e) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("roles", usuarioService.listarRoles());
            model.addAttribute("error", e.getMessage());
            return "usuario_form";
        }
    }

    @GetMapping("/admin/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long idUsuario) {
        usuarioService.eliminarLogico(idUsuario);
        return "redirect:/admin/usuarios";
    }
}