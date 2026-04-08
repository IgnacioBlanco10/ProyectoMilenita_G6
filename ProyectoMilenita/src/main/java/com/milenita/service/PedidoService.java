/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.EstadoPedido;
import com.milenita.domain.Pedido;
import com.milenita.repository.PedidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nacho
 */
@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido guardar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPorUsuario(Long idUsuario) {
        return pedidoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public void actualizarEstado(Long idPedido, EstadoPedido estado) {
        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido != null) {
            pedido.setEstado(estado);
            pedidoRepository.save(pedido);
        }
    }
}