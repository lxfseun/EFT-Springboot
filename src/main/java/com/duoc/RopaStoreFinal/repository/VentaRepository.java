    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.repository;

import com.duoc.RopaStoreFinal.model.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author faxin
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long>{
    boolean existsByPedidoId(Long pedidoId);
    Optional<Venta> findByPedidoId(Long pedidoId);
    List<Venta> findByPedidoClienteId(Long clienteId);
}
