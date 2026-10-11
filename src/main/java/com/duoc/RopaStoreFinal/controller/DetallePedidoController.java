/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.model.DetallePedido;
import com.duoc.RopaStoreFinal.service.DetallePedidoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<List<DetallePedido>> listar() {
        return ResponseEntity.ok(detallePedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedido> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(detallePedidoService.buscarPorId(id));
    }
}