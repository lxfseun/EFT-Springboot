/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.model.Inventario;
import com.duoc.RopaStoreFinal.service.InventarioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faxin
 */
@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {
    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listar() {
        return ResponseEntity.ok(inventarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.buscarPorId(id));
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Inventario> buscarPorProducto(@PathVariable Long idProducto) {
        return ResponseEntity.ok(inventarioService.buscarPorProducto(idProducto));
    }

    @PutMapping("/producto/{idProducto}/actualizar")
    public ResponseEntity<Inventario> actualizarStock(@PathVariable Long idProducto, @RequestParam int cantidad) {
        return ResponseEntity.ok(inventarioService.actualizarStock(idProducto, cantidad));
    }

    @PutMapping("/producto/{idProducto}/reemplazar")
    public ResponseEntity<Inventario> reemplazarStock(@PathVariable Long idProducto, @RequestParam int stock) {
        return ResponseEntity.ok(inventarioService.reemplazarStock(idProducto, stock));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}