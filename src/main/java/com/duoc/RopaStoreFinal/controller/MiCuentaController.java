/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.dto.PerfilRequest;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.model.Pedido;
import com.duoc.RopaStoreFinal.model.Venta;
import com.duoc.RopaStoreFinal.service.MiCuentaService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faxin
 */
@RestController
@RequestMapping("/api/mi")
@RequiredArgsConstructor
public class MiCuentaController {
    private final MiCuentaService miCuentaService;

    @GetMapping("/perfil")
    public ResponseEntity<Cliente> perfil(Authentication auth) {
        return ResponseEntity.ok(miCuentaService.obtenerPerfil(auth.getName()));
    }

    @PutMapping("/perfil")
    public ResponseEntity<Cliente> actualizarPerfil(Authentication auth, @Valid @RequestBody PerfilRequest datos) {
        return ResponseEntity.ok(miCuentaService.actualizarPerfil(auth.getName(), datos));
    }

    @PostMapping("/pedidos")
    public ResponseEntity<Pedido> crearPedido(Authentication auth, @RequestBody Pedido pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(miCuentaService.crearPedido(auth.getName(), pedido));
    }

    @GetMapping("/pedidos")
    public ResponseEntity<List<Pedido>> listarPedidos(Authentication auth) {
        return ResponseEntity.ok(miCuentaService.listarPedidos(auth.getName()));
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<Pedido> buscarPedido(Authentication auth, @PathVariable Long id) {
        return ResponseEntity.ok(miCuentaService.buscarPedido(auth.getName(), id));
    }

    @PutMapping("/pedidos/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(Authentication auth, @PathVariable Long id) {
        return ResponseEntity.ok(miCuentaService.cancelarPedido(auth.getName(), id));
    }

    @PostMapping("/pedidos/{id}/pagar")
    public ResponseEntity<Venta> pagarPedido(Authentication auth, @PathVariable Long id,
            @RequestParam(required = false) Long metodoPagoId) {
        return ResponseEntity.ok(miCuentaService.pagarPedido(auth.getName(), id, metodoPagoId));
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<Venta>> listarVentas(Authentication auth) {
        return ResponseEntity.ok(miCuentaService.listarVentas(auth.getName()));
    }
}
