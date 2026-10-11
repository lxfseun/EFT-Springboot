/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.EstadoPedido;
import com.duoc.RopaStoreFinal.model.EstadoVenta;
import com.duoc.RopaStoreFinal.model.Pedido;
import com.duoc.RopaStoreFinal.model.Venta;
import com.duoc.RopaStoreFinal.repository.PedidoRepository;
import com.duoc.RopaStoreFinal.repository.VentaRepository;
import jakarta.transaction.Transactional;
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
    private final PedidoService pedidoService;

    @Transactional
    public Venta crear(Venta venta) {
        if (venta.getPedido() == null || venta.getPedido().getId() == null) {
            throw new DatosInvalidosException("El pedido es obligatorio");
        }
        if (venta.getMetodoVenta() == null) {
            throw new DatosInvalidosException("El método de venta es obligatorio");
        }

        Pedido pedido = pedidoRepository.findById(venta.getPedido().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido asociado no existe"));

        if (pedido.getEstadoPedido() == EstadoPedido.CANCELADO) {
            throw new DatosInvalidosException("No se puede vender un pedido cancelado");
        }
        if (ventaRepository.existsByPedidoId(pedido.getId())) {
            throw new DatosInvalidosException("El pedido ya tiene una venta asociada");
        }

        venta.setId(null);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstadoVenta(EstadoVenta.PENDIENTE);
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

    @Transactional
    public Venta actualizarEstado(Long id, EstadoVenta nuevoEstado) {
        if (nuevoEstado != EstadoVenta.APROBADO && nuevoEstado != EstadoVenta.RECHAZADO) {
            throw new DatosInvalidosException("Solo se puede aprobar o rechazar una venta");
        }
        Venta venta = buscarPorId(id);
        if (venta.getEstadoVenta() != EstadoVenta.PENDIENTE) {
            throw new DatosInvalidosException("Solo una venta pendiente puede cambiar de estado");
        }

        venta.setEstadoVenta(nuevoEstado);
        if (nuevoEstado == EstadoVenta.APROBADO) {
            pedidoService.cambiarEstado(venta.getPedido(), EstadoPedido.PAGADO);
        } else {
            pedidoService.cambiarEstado(venta.getPedido(), EstadoPedido.CANCELADO);
        }
        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        Venta venta = buscarPorId(id);
        ventaRepository.delete(venta);
    }
}
