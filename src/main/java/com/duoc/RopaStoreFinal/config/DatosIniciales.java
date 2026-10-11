/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.config;

import com.duoc.RopaStoreFinal.model.Inventario;
import com.duoc.RopaStoreFinal.model.Producto;
import com.duoc.RopaStoreFinal.repository.InventarioRepository;
import com.duoc.RopaStoreFinal.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author faxin
 */
@Component
@RequiredArgsConstructor
public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) {
        if (productoRepository.count() > 0) {
            return;
        }
        crear("Polera básica blanca", "Polera de algodón", 9990, 50);
        crear("Jeans azul", "Jeans corte recto", 24990, 30);
        crear("Chaqueta de mezclilla", "Chaqueta clásica", 39990, 20);
        crear("Zapatillas urbanas", "Zapatillas blancas", 34990, 25);
        crear("Polerón con capucha", "Polerón gris", 19990, 40);
    }

    private void crear(String nombre, String descripcion, int precio, int stock) {
        Producto producto = new Producto();
        producto.setNombreProducto(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        productoRepository.save(producto);

        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setStock(stock);
        inventarioRepository.save(inventario);
    }
}
