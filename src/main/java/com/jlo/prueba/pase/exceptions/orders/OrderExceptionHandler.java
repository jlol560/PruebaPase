package com.jlo.prueba.pase.exceptions.orders;

import com.jlo.prueba.pase.utils.enums.OrderStatus;
import com.jlo.prueba.pase.controllers.orders.OrderController;
import com.jlo.prueba.pase.dtos.responses.ResponseOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice(assignableTypes = {OrderController.class})
public class OrderExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseOrder> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ResponseOrder response = ResponseOrder
                .error(HttpStatus.BAD_REQUEST.value(), "Errores de validación: " + String.join(", ", errors));
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ResponseOrder> handleOrderNotFound(OrderNotFoundException ex) {
        ResponseOrder response = ResponseOrder
                .error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseOrder> handleAllExceptions(Exception ex) {

        ResponseOrder response =
                ResponseOrder.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor");
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseOrder> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Valor inválido '%s' para el parámetro '%s'. Valores permitidos: %s",
                ex.getValue(),
                ex.getName(),
                Arrays.toString(OrderStatus.values()));

        ResponseOrder response = ResponseOrder.error(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseOrder> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        String errorMessage = "Valor de status inválido. Valores permitidos: " + Arrays.toString(OrderStatus.values());
        ResponseOrder response = ResponseOrder.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return ResponseEntity.badRequest().body(response);
    }
}
