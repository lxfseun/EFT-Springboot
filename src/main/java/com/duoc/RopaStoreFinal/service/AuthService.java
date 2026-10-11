/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.dto.LoginRequest;
import com.duoc.RopaStoreFinal.dto.LoginResponse;
import com.duoc.RopaStoreFinal.dto.RegistroRequest;
import com.duoc.RopaStoreFinal.exception.CredencialesInvalidasException;
import com.duoc.RopaStoreFinal.exception.DatosInvalidosException;
import com.duoc.RopaStoreFinal.model.Cliente;
import com.duoc.RopaStoreFinal.model.Rol;
import com.duoc.RopaStoreFinal.model.Usuario;
import com.duoc.RopaStoreFinal.repository.ClienteRepository;
import com.duoc.RopaStoreFinal.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public Cliente registrar(RegistroRequest request) {
        if (usuarioRepository.existsByCorreoUsuario(request.getCorreo())
                || clienteRepository.existsByCorreo(request.getCorreo())) {
            throw new DatosInvalidosException("El correo ya está registrado");
        }
        if (clienteRepository.existsByRut(request.getRut())) {
            throw new DatosInvalidosException("El rut ya está registrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNombreCliente(request.getNombreCliente());
        cliente.setApellidoCliente(request.getApellidoCliente());
        cliente.setRut(request.getRut());
        cliente.setCorreo(request.getCorreo());
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        Cliente guardado = clienteRepository.save(cliente);

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreCliente() + " " + request.getApellidoCliente());
        usuario.setCorreoUsuario(request.getCorreo());
        usuario.setContrasenna(passwordEncoder.encode(request.getContrasenna()));
        usuario.setRol(Rol.CLIENTE);
        usuarioRepository.save(usuario);

        return guardado;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByCorreoUsuario(request.getCorreo())
                .orElseThrow(() -> new CredencialesInvalidasException("Correo o contraseña incorrectos"));

        if (!passwordEncoder.matches(request.getContrasenna(), usuario.getContrasenna())) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario);
        return new LoginResponse(token, "Bearer", usuario.getCorreoUsuario(), usuario.getRol().name());
    }
}