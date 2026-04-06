/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.Factura;
import com.milenita.repository.FacturaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nacho
 */
@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    public Factura getFacturaConVentas(Long idFactura) {
        return facturaRepository.findById(idFactura).orElse(null);
    }

    public List<Factura> listarPorUsuario(Long idUsuario) {
        return facturaRepository.findByUsuario_IdUsuarioOrderByFechaDesc(idUsuario);
    }
}