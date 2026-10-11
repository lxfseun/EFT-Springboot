/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.config;

import com.duoc.RopaStoreFinal.model.Rol;
import com.duoc.RopaStoreFinal.model.Usuario;
import com.duoc.RopaStoreFinal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author faxin
 */
@Component
@RequiredArgsConstructor
public class AdminInicial implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.correo}")
    private String correo;

    @Value("${app.admin.password}")
    private String password;

    @Override
    public void run(String... args) {
        if (usuarioRepository.existsByCorreoUsuario(correo)) {
            return;
        }
        Usuario admin = new Usuario();
        admin.setNombreUsuario("Administrador");
        admin.setCorreoUsuario(correo);
        admin.setContrasenna(passwordEncoder.encode(password));
        admin.setRol(Rol.EMPLEADO);
        usuarioRepository.save(admin);
    }
}