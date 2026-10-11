/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author faxin
 */
@Data
public class PerfilRequest {

    @NotBlank(message="El nombre es obligatorio")
    private String nombreCliente;

    @NotBlank(message="El apellido es obligatorio")
    private String apellidoCliente;

    @NotBlank(message="La dirección es obligatoria")
    private String direccion;

    @NotBlank(message="El teléfono es obligatorio")
    private String telefono;
}