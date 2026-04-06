/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.domain;

import java.io.Serializable;
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
public class Item implements Serializable {

    private Producto producto;
    private int cantidad;
    private Double precioHistorico;

    public Double getSubTotal() {
        if (precioHistorico == null) {
            return 0.0;
        }
        return precioHistorico * cantidad;
    }
}