/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.model.Inventario;
import com.duoc.RopaStoreFinal.model.Producto;
import com.duoc.RopaStoreFinal.repository.InventarioRepository;
import com.duoc.RopaStoreFinal.repository.ProductoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public Inventario crear(Producto producto) {
        Inventario inventario = new Inventario();
        inventario.setId(null);
        inventario.setProducto(producto);
        inventario.setStock(0);
        return inventarioRepository.save(inventario);
    }

    public List<Inventario> listar() {
        return inventarioRepository.findAll();
    }

    public Inventario buscarPorId(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Inventario con id: " + id + " no encontrado."));
    }

    public Inventario actualizarStock(Long id, int cantidad) {
        Inventario inventario = buscarPorId(id);
        inventario.setStock(inventario.getStock() + cantidad);
        return inventarioRepository.save(inventario);
    }

    public void eliminar(Long id) {
        Inventario inventario = buscarPorId(id);
        inventarioRepository.delete(inventario);
    }    
}
