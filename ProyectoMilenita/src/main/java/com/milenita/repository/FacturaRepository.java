/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.milenita.repository;

import com.milenita.domain.Factura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nacho
 */
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    @EntityGraph(attributePaths = {"usuario", "ventas", "ventas.producto"})
    Optional<Factura> findById(Long idFactura);

    List<Factura> findByUsuario_IdUsuarioOrderByFechaDesc(Long idUsuario);
}
