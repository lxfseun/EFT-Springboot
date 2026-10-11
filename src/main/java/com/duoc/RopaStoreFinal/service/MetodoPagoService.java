/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.dto.MetodoPagoRequest;
import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.model.MetodoPago;
import com.duoc.RopaStoreFinal.model.MetodoVenta;
import com.duoc.RopaStoreFinal.repository.ClienteRepository;
import com.duoc.RopaStoreFinal.repository.MetodoPagoRepository;
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
public class MetodoPagoService {
    private final MetodoPagoRepository metodoPagoRepository;
    private final ClienteRepository clienteRepository;

    private Cliente obtenerCliente(String correo) {
        return clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado"));
    }

    private MetodoPago buscar(Cliente cliente, Long id) {
        return metodoPagoRepository.findByIdAndClienteId(id, cliente.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Método de pago id: " + id + " no encontrado"));
    }

    @Transactional
    public MetodoPago crear(String correo, MetodoPagoRequest request) {
        Cliente cliente = obtenerCliente(correo);

        if (request.getTipo() == MetodoVenta.TARJETA && !request.getDetalle().matches("\\d{4}")) {
            throw new DatosInvalidosException("Para una tarjeta indica solo los últimos 4 dígitos");
        }
        if (request.getTipo() == MetodoVenta.PAYPAL && !request.getDetalle().contains("@")) {
            throw new DatosInvalidosException("Para PayPal indica el correo de la cuenta");
        }

        MetodoPago metodo = new MetodoPago();
        metodo.setTipo(request.getTipo());
        metodo.setDetalle(request.getDetalle());
        metodo.setCliente(cliente);
        metodo.setPredeterminado(metodoPagoRepository.findByClienteId(cliente.getId()).isEmpty());
        return metodoPagoRepository.save(metodo);
    }

    public List<MetodoPago> listar(String correo) {
        return metodoPagoRepository.findByClienteId(obtenerCliente(correo).getId());
    }

    @Transactional
    public MetodoPago marcarPredeterminado(String correo, Long id) {
        Cliente cliente = obtenerCliente(correo);
        MetodoPago elegido = buscar(cliente, id);
        for (MetodoPago metodo : metodoPagoRepository.findByClienteId(cliente.getId())) {
            metodo.setPredeterminado(metodo.getId().equals(elegido.getId()));
            metodoPagoRepository.save(metodo);
        }
        return elegido;
    }

    @Transactional
    public void eliminar(String correo, Long id) {
        Cliente cliente = obtenerCliente(correo);
        MetodoPago metodo = buscar(cliente, id);
        metodoPagoRepository.delete(metodo);

        if (metodo.isPredeterminado()) {
            metodoPagoRepository.findByClienteId(cliente.getId()).stream().findFirst().ifPresent(otro -> {
                otro.setPredeterminado(true);
                metodoPagoRepository.save(otro);
            });
        }
    }

    public MetodoPago obtenerParaPago(Cliente cliente, Long metodoPagoId) {
        if (metodoPagoId != null) {
            return buscar(cliente, metodoPagoId);
        }
        return metodoPagoRepository.findByClienteIdAndPredeterminadoTrue(cliente.getId())
                .orElseThrow(() -> new DatosInvalidosException("Registra un método de pago antes de pagar"));
    }
}