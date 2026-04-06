/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.EstadoFactura;
import com.milenita.domain.Factura;
import com.milenita.domain.Item;
import com.milenita.domain.Producto;
import com.milenita.domain.Usuario;
import com.milenita.domain.Venta;
import com.milenita.repository.FacturaRepository;
import com.milenita.repository.ProductoRepository;
import com.milenita.repository.VentaRepository;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nacho
 */
@Service
public class CarritoService {

    private static final String ATRIBUTO_CARRITO = "carrito";

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @SuppressWarnings("unchecked")
    public List<Item> obtenerCarrito(HttpSession session) {
        List<Item> carrito = (List<Item>) session.getAttribute(ATRIBUTO_CARRITO);
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute(ATRIBUTO_CARRITO, carrito);
        }
        return carrito;
    }

    public void agregarProducto(HttpSession session, Long idProducto) {
        List<Item> carrito = obtenerCarrito(session);
        Producto producto = productoRepository.findById(idProducto).orElse(null);

        if (producto == null) {
            return;
        }

        for (Item item : carrito) {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                item.setCantidad(item.getCantidad() + 1);
                session.setAttribute(ATRIBUTO_CARRITO, carrito);
                return;
            }
        }

        carrito.add(new Item(producto, 1, producto.getPrecio()));
        session.setAttribute(ATRIBUTO_CARRITO, carrito);
    }

    public void modificarCantidad(HttpSession session, Long idProducto, int cantidad) {
        List<Item> carrito = obtenerCarrito(session);

        carrito.removeIf(item -> {
            if (item.getProducto().getIdProducto().equals(idProducto)) {
                if (cantidad <= 0) {
                    return true;
                }
                item.setCantidad(cantidad);
            }
            return false;
        });

        session.setAttribute(ATRIBUTO_CARRITO, carrito);
    }

    public void eliminarProducto(HttpSession session, Long idProducto) {
        List<Item> carrito = obtenerCarrito(session);
        carrito.removeIf(item -> item.getProducto().getIdProducto().equals(idProducto));
        session.setAttribute(ATRIBUTO_CARRITO, carrito);
    }

    public Double obtenerTotal(HttpSession session) {
        return obtenerCarrito(session)
                .stream()
                .mapToDouble(Item::getSubTotal)
                .sum();
    }

    public Integer obtenerCantidadItems(HttpSession session) {
        return obtenerCarrito(session)
                .stream()
                .mapToInt(Item::getCantidad)
                .sum();
    }

    public void limpiarCarrito(HttpSession session) {
        session.removeAttribute(ATRIBUTO_CARRITO);
    }

    @Transactional
    public Factura facturar(HttpSession session, Usuario usuario) {
        List<Item> carrito = obtenerCarrito(session);

        if (carrito.isEmpty()) {
            return null;
        }

        Factura factura = new Factura();
        factura.setUsuario(usuario);
        factura.setFecha(LocalDateTime.now());
        factura.setEstado(EstadoFactura.Pagada);
        factura.setTotal(obtenerTotal(session));

        factura = facturaRepository.save(factura);

        for (Item item : carrito) {
            Producto producto = item.getProducto();

            Venta venta = new Venta();
            venta.setFactura(factura);
            venta.setProducto(producto);
            venta.setCantidad(item.getCantidad());
            venta.setPrecioHistorico(item.getPrecioHistorico());

            ventaRepository.save(venta);

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);
        }

        limpiarCarrito(session);
        return factura;
    }
}
