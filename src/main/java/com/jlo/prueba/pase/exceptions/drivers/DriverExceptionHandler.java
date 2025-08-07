package com.jlo.prueba.pase.exceptions.drivers;

import com.jlo.prueba.pase.controllers.drivers.DriverController;
import com.jlo.prueba.pase.dtos.drivers.responses.DriverResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {DriverController.class})
public class DriverExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DriverResponse> handleAllExceptions(Exception ex) {
        DriverResponse response = DriverResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor: " + ex.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }
    @ExceptionHandler(LicenseNumberException.class)
    public ResponseEntity<DriverResponse> handleLicenseNumberException(LicenseNumberException ex) {
        DriverResponse response = DriverResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DriverResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Errores de validación: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );
        DriverResponse response = DriverResponse.error(HttpStatus.BAD_REQUEST.value(), errors.toString());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<DriverResponse> handleDriverNotFound(DriverNotFoundException ex) {
        DriverResponse response = DriverResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
