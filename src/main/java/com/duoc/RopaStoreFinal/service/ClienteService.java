/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.repository.ClienteRepository;
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
    
    public Cliente crear(Cliente cliente){
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }
    
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
        cliente.setCorreo(uCliente.getCorreo());
        cliente.setDireccion(uCliente.getDireccion());
        cliente.setTelefono(uCliente.getTelefono());
        return clienteRepository.save(cliente);
    }
    
    public void eliminar(Long id){
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}
