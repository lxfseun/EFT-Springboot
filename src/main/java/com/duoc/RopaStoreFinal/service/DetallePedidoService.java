/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.DetallePedido;
import com.duoc.RopaStoreFinal.model.Pedido;
import com.duoc.RopaStoreFinal.model.Producto;
import com.duoc.RopaStoreFinal.repository.DetallePedidoRepository;
import com.duoc.RopaStoreFinal.repository.PedidoRepository;
import com.duoc.RopaStoreFinal.repository.ProductoRepository;
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
public class DetallePedidoService {
    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final InventarioService inventarioService;

    @Transactional
    public DetallePedido crear(DetallePedido detallePedido) {
        if (detallePedido.getPedido() == null || detallePedido.getPedido().getId() == null) {
            throw new DatosInvalidosException("El pedido es obligatorio");
        }
        if (detallePedido.getProducto() == null || detallePedido.getProducto().getId() == null) {
            throw new DatosInvalidosException("El producto es obligatorio");
        }
        if (detallePedido.getCantidad() <= 0) {
            throw new DatosInvalidosException("La cantidad debe ser mayor a cero");
        }

        Pedido pedido = pedidoRepository.findById(detallePedido.getPedido().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(detallePedido.getProducto().getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        inventarioService.actualizarStock(producto.getId(), -detallePedido.getCantidad());

        detallePedido.setId(null);
        detallePedido.setPedido(pedido);
        detallePedido.setProducto(producto);
        pedido.setTotal(pedido.getTotal() + producto.getPrecio() * detallePedido.getCantidad());
        pedidoRepository.save(pedido);
        return detallePedidoRepository.save(detallePedido);
    }

    public List<DetallePedido> listar() {
        return detallePedidoRepository.findAll();
    }

    public DetallePedido buscarPorId(Long id) {
        return detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle de pedido id: " + id + " no encontrado"));
    }

    @Transactional
    public DetallePedido actualizar(Long id, DetallePedido detalleModificado) {
        if (detalleModificado.getCantidad() <= 0) {
            throw new DatosInvalidosException("La cantidad debe ser mayor a cero");
        }
        DetallePedido detalle = buscarPorId(id);
        Producto producto = detalle.getProducto();
        int diferencia = detalleModificado.getCantidad() - detalle.getCantidad();

        inventarioService.actualizarStock(producto.getId(), -diferencia);

        Pedido pedido = detalle.getPedido();
        pedido.setTotal(pedido.getTotal() + producto.getPrecio() * diferencia);
        pedidoRepository.save(pedido);

        detalle.setCantidad(detalleModificado.getCantidad());
        return detallePedidoRepository.save(detalle);
    }

    @Transactional
    public void eliminar(Long id) {
        DetallePedido detalle = buscarPorId(id);
        inventarioService.actualizarStock(detalle.getProducto().getId(), detalle.getCantidad());

        Pedido pedido = detalle.getPedido();
        pedido.setTotal(pedido.getTotal() - detalle.getProducto().getPrecio() * detalle.getCantidad());
        pedidoRepository.save(pedido);

        detallePedidoRepository.delete(detalle);
    }
}
