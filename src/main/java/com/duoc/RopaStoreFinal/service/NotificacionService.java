/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.model.Notificacion;
import com.duoc.RopaStoreFinal.repository.NotificacionRepository;
import static java.lang.StrictMath.log;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public void enviar(String correo, String asunto, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setCorreo(correo);
        notificacion.setAsunto(asunto);
        notificacion.setMensaje(mensaje);
        notificacion.setFecha(LocalDateTime.now());
        notificacionRepository.save(notificacion);
        log.info("Correo simulado a {} | {} | {}", correo, asunto, mensaje);
    }

    public List<Notificacion> listar() {
        return notificacionRepository.findAll();
    }

    public List<Notificacion> listarPorCorreo(String correo) {
        return notificacionRepository.findByCorreoOrderByFechaDesc(correo);
    }
}
