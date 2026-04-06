/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.milenita.repository;

import com.milenita.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nacho
 */
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
