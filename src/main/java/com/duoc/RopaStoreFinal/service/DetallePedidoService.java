/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.DetallePedido;
import com.duoc.RopaStoreFinal.repository.DetallePedidoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;

    public List<DetallePedido> listar() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido buscarPorId(Long id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle de pedido id: " + id + " no encontrado"));
    }
}
