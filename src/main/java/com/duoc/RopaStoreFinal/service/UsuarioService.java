/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Usuario;
import com.duoc.RopaStoreFinal.repository.UsuarioRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario crear(Usuario usuario) {
        usuario.setId(null);        
        usuario.setContrasenna(passwordEncoder.encode(usuario.getContrasenna()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario id: " + id + " no encontrado"));
    }

    public Usuario actualizar(Long id, Usuario usuarioModificado) {
        Usuario usuario = buscarPorId(id);
        usuario.setNombreUsuario(usuarioModificado.getNombreUsuario());
        usuario.setCorreoUsuario(usuarioModificado.getCorreoUsuario());
        usuario.setRol(usuarioModificado.getRol());
        if(usuarioModificado.getContrasenna() != null && !usuarioModificado.getContrasenna().isBlank()){
            usuario.setContrasenna(passwordEncoder.encode(usuarioModificado.getContrasenna()));
        }
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }
}