/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Producto;
import com.milenita.service.CategoriaService;
import com.milenita.service.ComentarioService;
import com.milenita.service.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/catalogo")
    public String catalogo(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) Long categoria,
            Model model) {

        List<Producto> productos;

        boolean hayBusqueda = busqueda != null && !busqueda.isBlank();
        boolean hayCategoria = categoria != null;

        if (hayBusqueda && hayCategoria) {
            productos = productoService.buscarYFiltrar(busqueda, categoria);
        } else if (hayBusqueda) {
            productos = productoService.buscarPorNombre(busqueda);
        } else if (hayCategoria) {
            productos = productoService.filtrarPorCategoria(categoria);
        } else {
            productos = productoService.listarActivos();
        }

        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("busqueda", busqueda);
        model.addAttribute("categoriaSeleccionada", categoria);

        return "catalogo";
    }

    @GetMapping("/producto/{id}")
    public String verDetalle(@PathVariable("id") Long idProducto, Model model) {
        Producto producto = productoService.obtenerPorId(idProducto);

        if (producto == null) {
            return "redirect:/catalogo";
        }

        model.addAttribute("producto", producto);
        model.addAttribute("masProductos", productoService.listarActivos());
        model.addAttribute("comentarios", comentarioService.listarPorProducto(idProducto));

        return "detalle";
    }
}