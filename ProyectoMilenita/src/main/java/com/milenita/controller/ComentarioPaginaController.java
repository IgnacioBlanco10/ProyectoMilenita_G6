/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author nacho
 */
@Controller
public class ComentarioPaginaController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/comentarios")
    public String verComentarios(Model model) {
        model.addAttribute("comentarios", comentarioService.listarTodos());
        return "comentarios";
    }
}
