/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Producto;
import com.milenita.service.CategoriaService;
import com.milenita.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/admin/productos")
    public String listarProductosAdmin(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "admin_productos";
    }

    @GetMapping("/admin/productos/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        Producto producto = new Producto();
        producto.setActivo(true);
        producto.setDestacado(false);

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarTodas());

        return "form_producto";
    }

    @GetMapping("/admin/productos/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long idProducto, Model model) {
        Producto producto = productoService.obtenerPorIdAdmin(idProducto);

        if (producto == null) {
            return "redirect:/admin/productos";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaService.listarTodas());

        return "form_producto";
    }

    @PostMapping("/admin/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardar(producto);
        return "redirect:/admin/productos";
    }

    @GetMapping("/admin/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Long idProducto) {
        productoService.eliminar(idProducto);
        return "redirect:/admin/productos";
    }
}
