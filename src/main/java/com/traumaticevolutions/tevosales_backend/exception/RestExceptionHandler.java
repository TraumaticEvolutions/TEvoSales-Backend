package com.traumaticevolutions.tevosales_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Manejador global de excepciones para la API REST.
 * Captura y maneja excepciones específicas para devolver respuestas HTTP
 * adecuadas.
 * 
 * @author Ángel Aragón
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Maneja excepciones de tipo NoSuchElementException.
     * Devuelve un código 404 Not Found con el mensaje de la excepción.
     *
     * @param ex la excepción capturada
     * @return ResponseEntity con el mensaje de error y código 404
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElement(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Maneja excepciones de tipo IllegalArgumentException.
     * Devuelve un código 400 Bad Request con el mensaje de la excepción.
     *
     * @param ex la excepción capturada
     * @return ResponseEntity con el mensaje de error y código 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Maneja excepciones generales.
     * Devuelve un código 500 Internal Server Error con un mensaje genérico.
     *
     * @param ex la excepción capturada
     * @return ResponseEntity con un mensaje de error y código 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + ex.getMessage());
    }

    /**
     * Maneja excepciones de tipo AccessDeniedException.
     * Devuelve un código 403 Forbidden con un mensaje de error.
     *
     * @param ex la excepción capturada
     * @return ResponseEntity con el mensaje de error y código 403
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para acceder a este recurso.");
    }
}