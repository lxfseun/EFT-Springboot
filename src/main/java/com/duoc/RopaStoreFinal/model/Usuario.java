/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Audited.Table;

/**
 *
 * @author faxin
 */
@Entity
@Table(name="usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "El usuario es obligatorio")
    private String nombreUsuario;
    
    @Email
    @Column(nullable = false, unique = true)
    @NotBlank(message = "El correo es obligatorio")
    private String correoUsuario;
    
    @Column(nullable=false)
    @NotBlank(message="La contraseña es obligatoria")
    private String contrasenna;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;
}
