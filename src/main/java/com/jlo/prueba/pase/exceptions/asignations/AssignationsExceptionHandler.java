package com.jlo.prueba.pase.exceptions.asignations;

import com.jlo.prueba.pase.controllers.asignations.AssignationController;
import com.jlo.prueba.pase.dtos.asignations.responses.AssignationResponse;
import com.jlo.prueba.pase.exceptions.drivers.DriverIsNotActiveException;
import com.jlo.prueba.pase.exceptions.drivers.DriverNotFoundException;
import com.jlo.prueba.pase.exceptions.orders.OrderIsNotCreatedException;
import com.jlo.prueba.pase.exceptions.orders.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

@RestControllerAdvice(assignableTypes = {AssignationController.class})
public class AssignationsExceptionHandler {


    @ExceptionHandler(FormatIdException.class)
    public ResponseEntity<AssignationResponse> handleFormatIdException(FormatIdException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AssignationResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(OrderIsNotCreatedException.class)
    public ResponseEntity<AssignationResponse> handleDriverIsNotActive(OrderIsNotCreatedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AssignationResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(DriverIsNotActiveException.class)
    public ResponseEntity<AssignationResponse> handleDriverIsNotActive(DriverIsNotActiveException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AssignationResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<AssignationResponse> handleDriverNotFound(DriverNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(AssignationResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<AssignationResponse> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(AssignationResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(OrderIsAlreadyAssignedException.class)
    public ResponseEntity<AssignationResponse> handleOrderIsAlreadyAssigned(OrderIsAlreadyAssignedException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AssignationResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(DirectoryCreationException.class)
    public ResponseEntity<AssignationResponse> handleDirectoryCreationException(DirectoryCreationException ex) {
        String errorMessage = "Error en el sistema de archivos: " + ex.getMessage();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AssignationResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        errorMessage
                ));
    }

    @ExceptionHandler(FilesEmptyException.class)
    public ResponseEntity<AssignationResponse> handleFilesEmptyException(FilesEmptyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AssignationResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<AssignationResponse> handleMultipartException(MultipartException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(AssignationResponse.error(HttpStatus.BAD_REQUEST.value(), "Error en el envío de archivos: " + ex.getMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AssignationResponse> handleIOException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AssignationResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al guardar el archivo: " + ex.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<AssignationResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AssignationResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor" + ": " + ex.getMessage()));
    }

}
