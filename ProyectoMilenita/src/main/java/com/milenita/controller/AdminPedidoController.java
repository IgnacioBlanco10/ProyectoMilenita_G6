/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.controller;

import com.milenita.domain.EstadoPedido;
import com.milenita.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author nacho
 */
@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        model.addAttribute("estados", EstadoPedido.values());
        return "admin_pedidos";
    }

    @PostMapping("/estado")
    public String actualizarEstado(@RequestParam("idPedido") Long idPedido,
                                   @RequestParam("estado") EstadoPedido estado) {
        pedidoService.actualizarEstado(idPedido, estado);
        return "redirect:/admin/pedidos";
    }
}
