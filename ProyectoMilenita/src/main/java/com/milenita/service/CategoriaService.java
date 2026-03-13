/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.Categoria;
import com.milenita.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodas() {
        return categoriaRepository.findByActivoTrue();
    }

    public Categoria obtenerPorId(Long idCategoria) {
        return categoriaRepository.findById(idCategoria).orElse(null);
    }  

    public void guardar(Categoria categoria) {
        categoriaRepository.save(categoria);
    }
}
