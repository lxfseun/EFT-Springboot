/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.model.DetallePedido;
import com.duoc.RopaStoreFinal.model.EstadoPedido;
import com.duoc.RopaStoreFinal.model.EstadoVenta;
import com.duoc.RopaStoreFinal.model.Pedido;
import com.duoc.RopaStoreFinal.model.Producto;
import com.duoc.RopaStoreFinal.repository.ClienteRepository;
import com.duoc.RopaStoreFinal.repository.PedidoRepository;
import com.duoc.RopaStoreFinal.repository.ProductoRepository;
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
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;
    private final InventarioService inventarioService;
    private final NotificacionService notificacionService;

    @Transactional
    public Pedido crear(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new DatosInvalidosException("El cliente es obligatorio");
        }
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            throw new DatosInvalidosException("El pedido debe tener al menos un producto");
        }

        Cliente cliente = clienteRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));

        int total = 0;
        for (DetallePedido detalle : pedido.getDetalles()) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new DatosInvalidosException("Cada detalle debe indicar un producto");
            }
            if (detalle.getCantidad() <= 0) {
                throw new DatosInvalidosException("La cantidad debe ser mayor a cero");
            }
            Producto producto = productoRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Producto id: " + detalle.getProducto().getId() + " no encontrado"));

            inventarioService.actualizarStock(producto.getId(), -detalle.getCantidad());

            detalle.setId(null);
            detalle.setProducto(producto);
            detalle.setPedido(pedido);
            total += producto.getPrecio() * detalle.getCantidad();
        }

        pedido.setId(null);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setCliente(cliente);
        pedido.setTotal(total);
        
        Pedido guardado = pedidoRepository.save(pedido);
        notificacionService.enviar(cliente.getCorreo(), "Pedido recibido",
                "Tu pedido #" + guardado.getId() + " fue recibido por un total de $" + guardado.getTotal());
        return guardado;
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido id: " + id + " no encontrado"));
    }

    @Transactional
    public Pedido actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) {
            throw new DatosInvalidosException("El estado es obligatorio");
        }
        if (nuevoEstado == EstadoPedido.PAGADO) {
            throw new DatosInvalidosException("El estado PAGADO se asigna al aprobar la venta del pedido");
        }
        return cambiarEstado(buscarPorId(id), nuevoEstado);
    }

    @Transactional
    public Pedido cambiarEstado(Pedido pedido, EstadoPedido nuevoEstado) {
        EstadoPedido actual = pedido.getEstadoPedido();
        if (!transicionValida(actual, nuevoEstado)) {
            throw new DatosInvalidosException("No se puede cambiar un pedido de " + actual + " a " + nuevoEstado);
        }

        if (nuevoEstado == EstadoPedido.CANCELADO || nuevoEstado == EstadoPedido.DEVUELTO) {
            reponerStock(pedido);
            ventaRepository.findByPedidoId(pedido.getId()).ifPresent(venta -> {
                if (venta.getEstadoVenta() == EstadoVenta.APROBADO) {
                    venta.setEstadoVenta(EstadoVenta.REEMBOLSADO);
                } else if (venta.getEstadoVenta() == EstadoVenta.PENDIENTE) {
                    venta.setEstadoVenta(EstadoVenta.RECHAZADO);
                }
                ventaRepository.save(venta);
            });
        }

        pedido.setEstadoPedido(nuevoEstado);
        Pedido guardado = pedidoRepository.save(pedido);
        notificacionService.enviar(pedido.getCliente().getCorreo(), "Pedido " + nuevoEstado,
                "Tu pedido #" + pedido.getId() + " ahora está en estado " + nuevoEstado);
        return guardado;
    }

    @Transactional
    public void eliminar(Long id) {
        Pedido pedido = buscarPorId(id);
        if (ventaRepository.existsByPedidoId(id)) {
            throw new DatosInvalidosException("No se puede eliminar un pedido que tiene una venta asociada");
        }
        if (pedido.getEstadoPedido() != EstadoPedido.CANCELADO
                && pedido.getEstadoPedido() != EstadoPedido.DEVUELTO) {
            reponerStock(pedido);
        }
        pedidoRepository.delete(pedido);
    }

    private boolean transicionValida(EstadoPedido actual, EstadoPedido nuevo) {
        return switch (actual) {
            case PENDIENTE -> nuevo == EstadoPedido.PAGADO || nuevo == EstadoPedido.CANCELADO;
            case PAGADO -> nuevo == EstadoPedido.EN_PREPARACION || nuevo == EstadoPedido.CANCELADO;
            case EN_PREPARACION -> nuevo == EstadoPedido.ENVIADO || nuevo == EstadoPedido.CANCELADO;
            case ENVIADO -> nuevo == EstadoPedido.ENTREGADO;
            case ENTREGADO -> nuevo == EstadoPedido.DEVUELTO;
            case CANCELADO, DEVUELTO -> false;
        };
    }

    private void reponerStock(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            inventarioService.actualizarStock(detalle.getProducto().getId(), detalle.getCantidad());
        }
    }
}