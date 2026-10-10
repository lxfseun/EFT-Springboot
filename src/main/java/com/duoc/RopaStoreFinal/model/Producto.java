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
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Audited.Table;

/**
 *
 * @author faxin
 */

@Entity
@Table(name="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor //descubrí que no es necesario escribir column en todos a menos que se vaya a personalizar, debido que jpa ya mapea el atributo sin esa anotación
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message="El nombre es obligatorio")
    private String nombreProducto;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(nullable = false)
    @Positive(message = "El precio debe ser mayor a 0") //no es necesario notnull porque ya el positive exige que sea mayor que 0, además un tipo primitivo no puede ser null
    private int precio;
}
