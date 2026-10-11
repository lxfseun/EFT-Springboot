/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.dto;

import com.duoc.RopaStoreFinal.model.MetodoVenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author faxin
 */
@Data
public class MetodoPagoRequest {

    @NotNull(message="El tipo es obligatorio")
    private MetodoVenta tipo;

    @NotBlank(message="El detalle es obligatorio")
    private String detalle;
}