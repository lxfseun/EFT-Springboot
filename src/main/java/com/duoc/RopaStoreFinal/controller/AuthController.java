/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.dto.LoginRequest;
import com.duoc.RopaStoreFinal.dto.LoginResponse;
import com.duoc.RopaStoreFinal.dto.RecuperarRequest;
import com.duoc.RopaStoreFinal.dto.RegistroRequest;
import com.duoc.RopaStoreFinal.dto.RestablecerRequest;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.service.AuthService;
import com.duoc.RopaStoreFinal.service.RecuperacionService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faxin
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RecuperacionService recuperacionService;

    @PostMapping("/registro")
    public ResponseEntity<Cliente> registrar(@Valid @RequestBody RegistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/recuperar")
    public ResponseEntity<Map<String, String>> recuperar(@Valid @RequestBody RecuperarRequest request) {
        recuperacionService.solicitar(request.getCorreo());
        return ResponseEntity.ok(Map.of("mensaje", "Si el correo existe, enviamos un código para restablecer la contraseña"));
    }

    @PostMapping("/restablecer")
    public ResponseEntity<Map<String, String>> restablecer(@Valid @RequestBody RestablecerRequest request) {
        recuperacionService.restablecer(request.getToken(), request.getNuevaContrasenna());
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada"));
    }
    
}
