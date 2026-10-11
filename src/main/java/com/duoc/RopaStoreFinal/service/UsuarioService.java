/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Rol;
import com.duoc.RopaStoreFinal.model.Usuario;
import com.duoc.RopaStoreFinal.repository.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario id: " + id + " no encontrado"));
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        if (usuario.getRol() == Rol.EMPLEADO) {
            throw new DatosInvalidosException("No se puede eliminar a un empleado");
        }
        usuarioRepository.delete(usuario);
    }
}