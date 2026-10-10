/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.duoc.RopaStoreFinal.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 *
 * @author faxin
 */
@Aspect //significa que es aop
@Component
@Slf4j //lombok y permite usar el log
public class LoggingAspect {

    @Around("execution(* com.duoc.RopaStoreFinal.service..*(..))") //a la ejecución de métodos, a todos las clases que estén dentro de service sin immportar la cantidad de param
    public Object registrarServicio(ProceedingJoinPoint joinPoint) throws Throwable {
        String metodo = joinPoint.getSignature().toShortString(); //intercepta el método, joinpoint representa el método por ejecución
        log.info("Ejecutando {}", metodo);
        long inicio = System.currentTimeMillis();
        Object resultado = joinPoint.proceed(); //permite la ejecución del método luego de interceptar
        long tiempo = System.currentTimeMillis() - inicio;
        log.info("Finalizado {} en {} ms", metodo, tiempo);
        return resultado;
    }

    @AfterThrowing(pointcut = "execution(* com.tiendaropa.tienda.service..*(..))", throwing = "ex")
    public void registrarError(JoinPoint joinPoint, Exception ex) {
        log.error("Error en {}: {}", joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}

