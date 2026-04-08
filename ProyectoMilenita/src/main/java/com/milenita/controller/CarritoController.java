/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Factura;
import com.milenita.repository.UsuarioRepository;
import com.milenita.service.CarritoService;
import com.milenita.service.FacturaService;
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
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        model.addAttribute("carritoItems", carritoService.obtenerCarrito(session));
        model.addAttribute("carritoTotal", carritoService.obtenerTotal(session));
        return "carrito";
    }

    @PostMapping("/carrito/agregar")
    public String agregar(@RequestParam("idProducto") Long idProducto, HttpSession session) {
        carritoService.agregarProducto(session, idProducto);
        return "redirect:/carrito";
    }

    @PostMapping("/carrito/modificar")
    public String modificar(@RequestParam("idProducto") Long idProducto,
                            @RequestParam("cantidad") int cantidad,
                            HttpSession session) {
        carritoService.modificarCantidad(session, idProducto, cantidad);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long idProducto, HttpSession session) {
        carritoService.eliminarProducto(session, idProducto);
        return "redirect:/carrito";
    }

    @GetMapping("/verFactura/{id}")
    public String verFactura(@PathVariable("id") Long idFactura, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Factura factura = facturaService.getFacturaConVentas(idFactura);
        if (factura == null) {
            return "redirect:/";
        }

        model.addAttribute("factura", factura);
        return "verFactura";
    }
}