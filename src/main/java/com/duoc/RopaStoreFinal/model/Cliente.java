/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message="El nombre es obligatorio")
    private String nombreCliente;
    
    @Column(nullable = false)
    @NotBlank(message="El apellido es obligatorio")
    private String apellidoCliente;
    
    @Column(nullable = false)
    @NotBlank(message="El rut es obligatorio")
    private String rut;
    
    @Email
    @Column(nullable = false)
    @NotBlank(message="El correo es obligatorio")
    private String correo;
    
    @Column(nullable = false)
    @NotBlank(message="la dirección es obligatoria")
    private String direccion;
    
    @Column(nullable = false)
    @NotBlank(message="El teléfono es obligatorio")
    private String telefono;
}
