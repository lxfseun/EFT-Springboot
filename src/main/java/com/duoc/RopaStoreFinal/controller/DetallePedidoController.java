/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.model.DetallePedido;
import com.duoc.RopaStoreFinal.service.DetallePedidoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faxin
 */
@RestController
@RequestMapping("/api/detalles-pedido")
@RequiredArgsConstructor
public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;

    @PostMapping
    public ResponseEntity<DetallePedido> crear(@Valid @RequestBody DetallePedido detallePedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detallePedidoService.crear(detallePedido));
    }

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listar() {
        return ResponseEntity.ok(detallePedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(detallePedidoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePedido> actualizar(@PathVariable Long id, @Valid @RequestBody DetallePedido detallePedido) {
        return ResponseEntity.ok(detallePedidoService.actualizar(id, detallePedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        detallePedidoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}