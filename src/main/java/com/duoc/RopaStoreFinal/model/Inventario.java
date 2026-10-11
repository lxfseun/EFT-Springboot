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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Table;

/**
 *
 * @author faxin
 */

@Entity
@Table(name="inventarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
        
    @Column(nullable = false)
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private int stock;
    
    @OneToOne
    @JoinColumn(name="producto_id", nullable = false, unique = true)
    private Producto producto;
}
