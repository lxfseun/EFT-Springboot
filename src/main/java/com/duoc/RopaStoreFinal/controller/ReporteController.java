/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.controller;

import com.duoc.RopaStoreFinal.dto.ProductoVendido;
import com.duoc.RopaStoreFinal.dto.ResumenVentas;
import com.duoc.RopaStoreFinal.service.ReporteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faxin
 */
@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<List<ProductoVendido>> masVendidos(@RequestParam(defaultValue = "5") int limite) {
        return ResponseEntity.ok(reporteService.productosMasVendidos(limite));
    }

    @GetMapping("/resumen")
    public ResponseEntity<ResumenVentas> resumen() {
        return ResponseEntity.ok(reporteService.resumen());
    }
}
