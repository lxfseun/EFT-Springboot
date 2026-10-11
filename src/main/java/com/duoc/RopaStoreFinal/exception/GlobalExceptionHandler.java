/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 *
 * @author faxin
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> crearRespuesta(HttpStatus status, Object mensaje) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("fecha", LocalDateTime.now());
        cuerpo.put("status", status.value());
        cuerpo.put("mensaje", mensaje);
        return new ResponseEntity<>(cuerpo, status);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return crearRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<Map<String, Object>> manejarStock(StockInsuficienteException ex) {
        return crearRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<Map<String, Object>> manejarDatosInvalidos(DatosInvalidosException ex) {
        return crearRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
        return crearRespuesta(HttpStatus.BAD_REQUEST, errores);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        log.warn("Cuerpo de la petición no válido: {}", ex.getMostSpecificCause().getMessage());
        return crearRespuesta(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es válido");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarIntegridad(DataIntegrityViolationException ex) {
        return crearRespuesta(HttpStatus.CONFLICT, "El registro ya existe o está relacionado con otros datos");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarGeneral(Exception ex) {
        return crearRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> manejarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        return crearRespuesta(HttpStatus.BAD_REQUEST, "Valor inválido para el parámetro: " + ex.getName());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> manejarParametroFaltante(MissingServletRequestParameterException ex) {
        return crearRespuesta(HttpStatus.BAD_REQUEST, "Falta el parámetro: " + ex.getParameterName());
    }
    
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> manejarCredenciales(CredencialesInvalidasException ex) {
        return crearRespuesta(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, Object>> manejarMetodoNoPermitido(HttpRequestMethodNotSupportedException ex) {
        return crearRespuesta(HttpStatus.METHOD_NOT_ALLOWED, "Método " + ex.getMethod() + " no permitido para esta ruta");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> manejarRutaInexistente(NoResourceFoundException ex) {
        return crearRespuesta(HttpStatus.NOT_FOUND, "La ruta no existe");
    }
}
