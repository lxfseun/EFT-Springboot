/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.exception.RecursoNoEncontradoException;
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
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;
    private final InventarioService inventarioService;
    
    
    @Transactional
    public Producto crear(Producto producto){
        producto.setId(null);
        Producto nuevoProducto = productoRepository.save(producto);
        inventarioService.crear(nuevoProducto.getId());
        return nuevoProducto;
    }
    
    public List<Producto> listar(){
        return productoRepository.findAll();
    }
    
    public Producto buscarPorId(Long id){
        return productoRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Producto con id: "+id+" no encontrado."));
    }
    
    public Producto actualizar(Long id, Producto uProducto){
        Producto producto = buscarPorId(id);
        producto.setNombreProducto(uProducto.getNombreProducto());
        producto.setDescripcion(uProducto.getDescripcion());
        producto.setPrecio(uProducto.getPrecio());
        return productoRepository.save(producto);
    }
    
    @Transactional
    public void eliminar(Long id){
        Producto producto = buscarPorId(id);
        inventarioRepository.findByProductoId(producto.getId())
                .ifPresent(inventario -> inventarioRepository.delete(inventario));
        productoRepository.delete(producto);
    }
}
