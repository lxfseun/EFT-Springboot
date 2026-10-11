/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.service;

import com.duoc.RopaStoreFinal.dto.ProductoVendido;
import com.duoc.RopaStoreFinal.dto.ResumenVentas;
import com.duoc.RopaStoreFinal.model.EstadoVenta;
import com.duoc.RopaStoreFinal.repository.VentaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author faxin
 */
@Service
@RequiredArgsConstructor
public class ReporteService {

    private final VentaRepository ventaRepository;

    public List<ProductoVendido> productosMasVendidos(int limite) {
        return ventaRepository.masVendidos(EstadoVenta.APROBADO).stream().limit(limite).toList();
    }

    public ResumenVentas resumen() {
        return new ResumenVentas(
                ventaRepository.countByEstadoVenta(EstadoVenta.APROBADO),
                ventaRepository.countByEstadoVenta(EstadoVenta.REEMBOLSADO),
                ventaRepository.totalPorEstado(EstadoVenta.APROBADO));
    }
}