/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author nacho
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {

    private Producto producto;
    private Integer cantidad;

    public Double getSubtotal() {
        if (producto == null || producto.getPrecio() == null || cantidad == null) {
            return 0.0;
        }
        return producto.getPrecio() * cantidad;
    }
}
