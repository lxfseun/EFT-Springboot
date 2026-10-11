/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author faxin
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String tipo;
    private String correo;
    private String rol;
}
