    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.repository;

import com.duoc.RopaStoreFinal.dto.ProductoVendido;
import com.duoc.RopaStoreFinal.model.EstadoVenta;
import com.duoc.RopaStoreFinal.model.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    
    @Query("SELECT new com.duoc.RopaStoreFinal.dto.ProductoVendido(pr.id, pr.nombreProducto, SUM(d.cantidad), SUM(d.cantidad * pr.precio)) "
        + "FROM Venta v JOIN v.pedido p JOIN p.detalles d JOIN d.producto pr "
        + "WHERE v.estadoVenta = :estado "
        + "GROUP BY pr.id, pr.nombreProducto "
        + "ORDER BY SUM(d.cantidad) DESC")
            
    List<ProductoVendido> masVendidos(@Param("estado") EstadoVenta estado);
    
    long countByEstadoVenta(EstadoVenta estado);

    @Query("SELECT COALESCE(SUM(p.total), 0) FROM Venta v JOIN v.pedido p WHERE v.estadoVenta = :estado")
    long totalPorEstado(@Param("estado") EstadoVenta estado);
}
