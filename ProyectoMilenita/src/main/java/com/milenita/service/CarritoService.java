/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.ItemCarrito;
import com.milenita.domain.Producto;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author nacho
 */
@Service
public class CarritoService {

    private static final String CARRITO_SESSION = "carrito";

    @SuppressWarnings("unchecked")
    public List<ItemCarrito> obtenerCarrito(HttpSession session) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute(CARRITO_SESSION);

        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute(CARRITO_SESSION, carrito);
        }

        return carrito;
    }

    public void agregarProducto(HttpSession session, Producto producto) {
        List<ItemCarrito> carrito = obtenerCarrito(session);

        for (ItemCarrito item : carrito) {
            if (item.getProducto().getIdProducto().equals(producto.getIdProducto())) {
                item.setCantidad(item.getCantidad() + 1);
                session.setAttribute(CARRITO_SESSION, carrito);
                return;
            }
        }

        carrito.add(new ItemCarrito(producto, 1));
        session.setAttribute(CARRITO_SESSION, carrito);
    }

    public void aumentarCantidad(HttpSession session, Long idProducto) {
        List<ItemCarrito> carrito = obtenerCarrito(session);

        for (ItemCarrito item : carrito) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                item.setCantidad(item.getCantidad() + 1);
                break;
            }
        }

        session.setAttribute(CARRITO_SESSION, carrito);
    }

    public void disminuirCantidad(HttpSession session, Long idProducto) {
        List<ItemCarrito> carrito = obtenerCarrito(session);

        carrito.removeIf(item -> {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                int nuevaCantidad = item.getCantidad() - 1;
                if (nuevaCantidad <= 0) {
                    return true;
                }
                item.setCantidad(nuevaCantidad);
            }
            return false;
        });

        session.setAttribute(CARRITO_SESSION, carrito);
    }

    public void eliminarProducto(HttpSession session, Long idProducto) {
        List<ItemCarrito> carrito = obtenerCarrito(session);
        carrito.removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
        session.setAttribute(CARRITO_SESSION, carrito);
    }

    public Double obtenerTotal(HttpSession session) {
        return obtenerCarrito(session).stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();
    }

    public Integer obtenerCantidadTotal(HttpSession session) {
        return obtenerCarrito(session).stream()
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }

    public void vaciarCarrito(HttpSession session) {
        session.removeAttribute(CARRITO_SESSION);
    }
}
