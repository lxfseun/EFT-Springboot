/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.model.Recuperacion;
import com.duoc.RopaStoreFinal.model.Usuario;
import com.duoc.RopaStoreFinal.repository.RecuperacionRepository;
import com.duoc.RopaStoreFinal.repository.UsuarioRepository;
import java.util.UUID;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class RecuperacionService {

    private final UsuarioRepository usuarioRepository;
    private final RecuperacionRepository recuperacionRepository;
    private final PasswordEncoder passwordEncoder;
    private final NotificacionService notificacionService;

    @Transactional
    public void solicitar(String correo) {
        usuarioRepository.findByCorreoUsuario(correo).ifPresent(usuario -> {
            Recuperacion recuperacion = new Recuperacion();
            recuperacion.setToken(UUID.randomUUID().toString());
            recuperacion.setCorreo(correo);
            recuperacion.setExpiracion(LocalDateTime.now().plusMinutes(15));
            recuperacionRepository.save(recuperacion);
            notificacionService.enviar(correo, "Recuperar contraseña",
                    "Tu código para restablecer la contraseña es: " + recuperacion.getToken());
        });
    }

    @Transactional
    public void restablecer(String token, String nuevaContrasenna) {
        Recuperacion recuperacion = recuperacionRepository.findByToken(token)
                .orElseThrow(() -> new DatosInvalidosException("Código inválido"));
        if (recuperacion.isUsado() || recuperacion.getExpiracion().isBefore(LocalDateTime.now())) {
            throw new DatosInvalidosException("El código expiró o ya fue usado");
        }
        Usuario usuario = usuarioRepository.findByCorreoUsuario(recuperacion.getCorreo())
                .orElseThrow(() -> new DatosInvalidosException("Código inválido"));
        usuario.setContrasenna(passwordEncoder.encode(nuevaContrasenna));
        usuarioRepository.save(usuario);
        recuperacion.setUsado(true);
        recuperacionRepository.save(recuperacion);
    }
}
