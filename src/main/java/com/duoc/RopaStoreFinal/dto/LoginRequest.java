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
public class LoginRequest {

    @NotBlank(message="El correo es obligatorio")
    private String correo;

    @NotBlank(message="La contraseña es obligatoria")
    private String contrasenna;
}

