/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.Comentario;
import com.milenita.domain.Producto;
import com.milenita.domain.Usuario;
import com.milenita.repository.UsuarioRepository;
import com.milenita.service.ComentarioService;
import com.milenita.service.ProductoService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author nacho
 */
@Controller
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/comentario/guardar")
    public String guardarComentario(@RequestParam("productoId") Long productoId,
            @RequestParam("contenido") String contenido,
            @RequestParam("calificacion") Integer calificacion,
            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        if (contenido == null || contenido.isBlank()) {
            return "redirect:/producto/" + productoId + "?errorComentario";
        }

        Producto producto = productoService.obtenerPorId(productoId);
        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElse(null);

        if (producto == null || usuario == null) {
            return "redirect:/catalogo";
        }

        Comentario comentario = new Comentario();
        comentario.setContenido(contenido);
        comentario.setCalificacion(calificacion);
        comentario.setProducto(producto);
        comentario.setUsuario(usuario);

        comentarioService.guardar(comentario);

        return "redirect:/producto/" + productoId + "?comentarioExitoso";
    }

    @GetMapping("/admin/comentario/eliminar/{id}")
    public String eliminarComentario(@PathVariable("id") Long idComentario,
            @RequestParam("productoId") Long productoId) {
        comentarioService.eliminar(idComentario);
        return "redirect:/producto/" + productoId;
    }
}
