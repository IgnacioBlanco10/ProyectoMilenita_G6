/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.milenita.service;

import com.milenita.domain.Comentario;
import com.milenita.repository.ComentarioRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nacho
 */
@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    public List<Comentario> listarPorProducto(Long idProducto) {
        return comentarioRepository.findByProducto_IdProductoOrderByFechaDesc(idProducto);
    }

    public List<Comentario> listarTodos() {
        return comentarioRepository.findAllByOrderByFechaDesc();
    }

    public void guardar(Comentario comentario) {
        comentario.setFecha(LocalDateTime.now());
        comentarioRepository.save(comentario);
    }

    public void eliminar(Long idComentario) {
        comentarioRepository.deleteById(idComentario);
    }
}
