package com.jlo.prueba.pase.exceptions.orders;

public class OrderIsNotCreatedException extends RuntimeException {

    public OrderIsNotCreatedException(String message) {
        super(message);
    }

    public OrderIsNotCreatedException() {
        super("La orden no fue creada.");
    }
}
