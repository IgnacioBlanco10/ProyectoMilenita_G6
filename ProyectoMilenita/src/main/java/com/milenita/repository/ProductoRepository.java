/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.milenita.repository;

import com.milenita.domain.Producto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByActivoTrue();

    List<Producto> findByActivoTrueAndDestacadoTrue();

    List<Producto> findByActivoTrueAndNombreContainingIgnoreCase(String nombre);

    List<Producto> findByActivoTrueAndCategoria_IdCategoria(Long idCategoria);

    Optional<Producto> findByIdProductoAndActivoTrue(Long idProducto);
}