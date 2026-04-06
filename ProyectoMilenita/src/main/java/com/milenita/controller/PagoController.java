/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Factura;
import com.milenita.domain.Usuario;
import com.milenita.repository.UsuarioRepository;
import com.milenita.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author nacho
 */
@Controller
public class PagoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/pago")
    public String mostrarPago(HttpSession session, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (carritoService.obtenerCarrito(session).isEmpty()) {
            return "redirect:/carrito";
        }

        model.addAttribute("total", carritoService.obtenerTotal(session));
        return "pago";
    }

    @PostMapping("/pago/confirmar")
    public String confirmarPago(@RequestParam("nombreTarjeta") String nombreTarjeta,
                                @RequestParam("numeroTarjeta") String numeroTarjeta,
                                @RequestParam("vencimiento") String vencimiento,
                                @RequestParam("cvv") String cvv,
                                @RequestParam("direccion") String direccion,
                                HttpSession session,
                                Principal principal,
                                Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        if (carritoService.obtenerCarrito(session).isEmpty()) {
            return "redirect:/carrito";
        }

        if (nombreTarjeta.isBlank() || numeroTarjeta.isBlank() || vencimiento.isBlank()
                || cvv.isBlank() || direccion.isBlank()) {
            model.addAttribute("total", carritoService.obtenerTotal(session));
            model.addAttribute("error", "Debes completar todos los campos.");
            return "pago";
        }

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElse(null);
        if (usuario == null) {
            return "redirect:/login";
        }

        Factura factura = carritoService.facturar(session, usuario);
        if (factura == null) {
            return "redirect:/carrito";
        }

        return "redirect:/verFactura/" + factura.getIdFactura();
    }
}
