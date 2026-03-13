/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.Producto;
import com.milenita.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

    public List<Producto> listarDestacados() {
        return productoRepository.findByActivoTrueAndDestacadoTrue();
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByActivoTrueAndNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> filtrarPorCategoria(Long idCategoria) {
        return productoRepository.findByActivoTrueAndCategoria_IdCategoria(idCategoria);
    }

    public Producto obtenerPorId(Long idProducto) {
        return productoRepository.findByIdProductoAndActivoTrue(idProducto).orElse(null);
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public void guardar(Producto producto) {
        productoRepository.save(producto);
    }

    public void eliminar(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElse(null);

        if (producto != null) {
            producto.setActivo(false);
            productoRepository.save(producto);
        }
    }
}