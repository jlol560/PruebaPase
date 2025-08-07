package com.jlo.prueba.pase.exceptions.asignations;

public class OrderIsAlreadyAssignedException extends RuntimeException {

    public OrderIsAlreadyAssignedException(String message) {
        super(message);
    }

    public OrderIsAlreadyAssignedException() {
        super("La orden ya está asignada a un conductor.");
    }
}
