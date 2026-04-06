/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.domain;

/**
 *
 * @author nacho
 */
public enum EstadoFactura {
    Activa("Activa"),
    Pagada("Pagada"),
    Anulada("Anulada");

    private final String valorBD;

    EstadoFactura(String valorBD) {
        this.valorBD = valorBD;
    }

    public String getValorBD() {
        return valorBD;
    }
}