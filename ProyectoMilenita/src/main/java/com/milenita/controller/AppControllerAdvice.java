/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Usuario;
import com.milenita.repository.UsuarioRepository;
import com.milenita.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 *
 * @author nacho
 */
@ControllerAdvice
public class AppControllerAdvice {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarritoService carritoService;

    @ModelAttribute("usuarioLogueado")
    public Usuario usuarioLogueado(Principal principal) {
        if (principal == null) {
            return null;
        }
        return usuarioRepository.findByCorreo(principal.getName()).orElse(null);
    }

    @ModelAttribute("cantidadCarrito")
    public Integer cantidadCarrito(HttpSession session) {
        return carritoService.obtenerCantidadItems(session);
    }
}