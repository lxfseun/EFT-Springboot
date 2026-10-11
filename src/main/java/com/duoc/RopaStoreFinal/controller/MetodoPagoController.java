/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.dto.MetodoPagoRequest;
import com.duoc.RopaStoreFinal.model.MetodoPago;
import com.duoc.RopaStoreFinal.service.MetodoPagoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/api/mi/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {
    private final MetodoPagoService metodoPagoService;

    @PostMapping
    public ResponseEntity<MetodoPago> crear(Authentication auth, @Valid @RequestBody MetodoPagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(metodoPagoService.crear(auth.getName(), request));
    }

    @GetMapping
    public ResponseEntity<List<MetodoPago>> listar(Authentication auth) {
        return ResponseEntity.ok(metodoPagoService.listar(auth.getName()));
    }

    @PutMapping("/{id}/predeterminado")
    public ResponseEntity<MetodoPago> marcarPredeterminado(Authentication auth, @PathVariable Long id) {
        return ResponseEntity.ok(metodoPagoService.marcarPredeterminado(auth.getName(), id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(Authentication auth, @PathVariable Long id) {
        metodoPagoService.eliminar(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }
}