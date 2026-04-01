/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Producto;
import com.milenita.service.CarritoService;
import com.milenita.service.ProductoService;
import jakarta.servlet.http.HttpSession;
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
    private ProductoService productoService;

    @GetMapping("/carrito")
    public String verCarrito(Model model, HttpSession session) {
        model.addAttribute("items", carritoService.obtenerCarrito(session));
        model.addAttribute("total", carritoService.obtenerTotal(session));
        return "carrito";
    }

    @GetMapping("/carrito/agregar/{id}")
    public String agregarProducto(@PathVariable("id") Long idProducto, HttpSession session) {
        Producto producto = productoService.obtenerPorId(idProducto);

        if (producto != null) {
            carritoService.agregarProducto(session, producto);
        }

        return "redirect:/carrito";
    }

    @GetMapping("/carrito/aumentar/{id}")
    public String aumentarCantidad(@PathVariable("id") Long idProducto, HttpSession session) {
        carritoService.aumentarCantidad(session, idProducto);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/disminuir/{id}")
    public String disminuirCantidad(@PathVariable("id") Long idProducto, HttpSession session) {
        carritoService.disminuirCantidad(session, idProducto);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long idProducto, HttpSession session) {
        carritoService.eliminarProducto(session, idProducto);
        return "redirect:/carrito";
    }

    @GetMapping("/carrito/vaciar")
    public String vaciarCarrito(HttpSession session) {
        carritoService.vaciarCarrito(session);
        return "redirect:/carrito";
    }
}
