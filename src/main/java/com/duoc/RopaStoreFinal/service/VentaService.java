/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.EstadoVenta;
import com.duoc.RopaStoreFinal.model.Pedido;
import com.duoc.RopaStoreFinal.model.Venta;
import com.duoc.RopaStoreFinal.repository.PedidoRepository;
import com.duoc.RopaStoreFinal.repository.VentaRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */

@Service
@RequiredArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepository;
    private final PedidoRepository pedidoRepository;

    public Venta crear(Venta venta) {
        Pedido pedido = pedidoRepository.findById(venta.getPedido().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido asociado no existe"));

        venta.setId(null);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setPedido(pedido);
        return ventaRepository.save(venta);
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta id: " + id + " no encontrada"));
    }

    public Venta actualizarEstado(Long id, EstadoVenta nuevoEstado) {
        Venta venta = buscarPorId(id);
        venta.setEstadoVenta(nuevoEstado);
        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        Venta venta = buscarPorId(id);
        ventaRepository.delete(venta);
    }
}

