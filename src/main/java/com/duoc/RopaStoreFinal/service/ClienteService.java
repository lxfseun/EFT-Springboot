/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.repository.ClienteRepository;
import com.duoc.RopaStoreFinal.repository.MetodoPagoRepository;
import com.duoc.RopaStoreFinal.repository.UsuarioRepository;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final MetodoPagoRepository metodoPagoRepository;

    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con id " + id + " no encontrado"));
    }

    public Cliente actualizar(Long id, Cliente uCliente){
        Cliente cliente = buscarPorId(id);
        cliente.setNombreCliente(uCliente.getNombreCliente());
        cliente.setApellidoCliente(uCliente.getApellidoCliente());
        cliente.setRut(uCliente.getRut());
        cliente.setDireccion(uCliente.getDireccion());
        cliente.setTelefono(uCliente.getTelefono());
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminar(Long id){
        Cliente cliente = buscarPorId(id);
        metodoPagoRepository.deleteAll(metodoPagoRepository.findByClienteId(id));
        clienteRepository.delete(cliente);
        usuarioRepository.findByCorreoUsuario(cliente.getCorreo())
                .ifPresent(usuarioRepository::delete);
    }
}