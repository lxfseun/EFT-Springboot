/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.dto.PerfilRequest;
import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.model.EstadoPedido;
import com.duoc.RopaStoreFinal.model.EstadoVenta;
import com.duoc.RopaStoreFinal.model.MetodoPago;
import com.duoc.RopaStoreFinal.model.Pedido;
import com.duoc.RopaStoreFinal.model.Venta;
import com.duoc.RopaStoreFinal.repository.ClienteRepository;
import com.duoc.RopaStoreFinal.repository.PedidoRepository;
import com.duoc.RopaStoreFinal.repository.VentaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class MiCuentaService {
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final VentaRepository ventaRepository;
    private final PedidoService pedidoService;
    private final VentaService ventaService;
    private final MetodoPagoService metodoPagoService;

    public Cliente obtenerPerfil(String correo) {
        return clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
    }

    public Cliente actualizarPerfil(String correo, PerfilRequest datos) {
        Cliente cliente = obtenerPerfil(correo);
        cliente.setNombreCliente(datos.getNombreCliente());
        cliente.setApellidoCliente(datos.getApellidoCliente());
        cliente.setDireccion(datos.getDireccion());
        cliente.setTelefono(datos.getTelefono());
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Pedido crearPedido(String correo, Pedido pedido) {
        pedido.setCliente(obtenerPerfil(correo));
        return pedidoService.crear(pedido);
    }

    public List<Pedido> listarPedidos(String correo) {
        return pedidoRepository.findByClienteId(obtenerPerfil(correo).getId());
    }

    public Pedido buscarPedido(String correo, Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        if (!pedido.getCliente().getCorreo().equals(correo)) {
            throw new RecursoNoEncontradoException("Pedido id: " + id + " no encontrado");
        }
        return pedido;
    }

    @Transactional
    public Pedido cancelarPedido(String correo, Long id) {
        Pedido pedido = buscarPedido(correo, id);
        if (pedido.getEstadoPedido() != EstadoPedido.PENDIENTE
                && pedido.getEstadoPedido() != EstadoPedido.PAGADO) {
            throw new DatosInvalidosException("Solo se puede cancelar un pedido pendiente o pagado");
        }
        return pedidoService.cambiarEstado(pedido, EstadoPedido.CANCELADO);
    }

    @Transactional
    public Venta pagarPedido(String correo, Long id, Long metodoPagoId) {
        Pedido pedido = buscarPedido(correo, id);
        MetodoPago metodoPago = metodoPagoService.obtenerParaPago(obtenerPerfil(correo), metodoPagoId);

        Venta venta = new Venta();
        venta.setPedido(pedido);
        venta.setMetodoVenta(metodoPago.getTipo());
        Venta creada = ventaService.crear(venta);
        return ventaService.actualizarEstado(creada.getId(), EstadoVenta.APROBADO);
    }

    public List<Venta> listarVentas(String correo) {
        return ventaRepository.findByPedidoClienteId(obtenerPerfil(correo).getId());
    }
}
