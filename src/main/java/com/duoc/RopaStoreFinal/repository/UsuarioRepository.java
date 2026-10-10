/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.repository;

import com.duoc.RopaStoreFinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author faxin
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
