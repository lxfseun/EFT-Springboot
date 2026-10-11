/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author faxin
 */
@Data
public class RegistroRequest {

    @NotBlank(message="El nombre es obligatorio")
    private String nombreCliente;

    @NotBlank(message="El apellido es obligatorio")
    private String apellidoCliente;

    @NotBlank(message="El rut es obligatorio")
    private String rut;

    @Email(message="El correo no es válido")
    @NotBlank(message="El correo es obligatorio")
    private String correo;

    @NotBlank(message="La dirección es obligatoria")
    private String direccion;

    @NotBlank(message="El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message="La contraseña es obligatoria")
    @Size(min = 6, message="La contraseña debe tener al menos 6 caracteres")
    private String contrasenna;
}
