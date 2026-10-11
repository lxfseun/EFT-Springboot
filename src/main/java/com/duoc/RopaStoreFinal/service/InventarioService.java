/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
import com.duoc.RopaStoreFinal.exception.StockInsuficienteException;
import com.duoc.RopaStoreFinal.model.Inventario;
import com.duoc.RopaStoreFinal.model.Producto;
import com.duoc.RopaStoreFinal.repository.InventarioRepository;
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
public class InventarioService {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public Inventario crear(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
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

    @Transactional                                                          //esto hace que si algo falla durante los cambios, los cambios se deshacen
    public Inventario actualizarStock(Long idProducto, int cantidad) {
        Inventario inventario = buscarPorProducto(idProducto);
        int nuevoStock = inventario.getStock() + cantidad;

        if (nuevoStock < 0) {
            throw new StockInsuficienteException("Stock insuficiente para el producto id: " + idProducto
                    + ". Stock actual: " + inventario.getStock());
        }
        inventario.setStock(nuevoStock);
        return inventarioRepository.save(inventario);
    }
    
    @Transactional
    public Inventario reemplazarStock(Long idProducto, int stock) {
        if (stock < 0) {
            throw new DatosInvalidosException("El stock no puede ser negativo");
        }
        Inventario inventario = buscarPorProducto(idProducto);
        inventario.setStock(stock);
        return inventarioRepository.save(inventario);
    }
    
    public Inventario buscarPorProducto(Long idProducto) {
        return inventarioRepository.findByProductoId(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Inventario del producto id: " + idProducto + " no encontrado."));
    }

    public void eliminar(Long id) {
        Inventario inventario = buscarPorId(id);
        inventarioRepository.delete(inventario);
    }    
}
