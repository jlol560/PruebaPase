package com.jlo.prueba.pase.exceptions.orders;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID orderId) {
        super("No se encontró la orden con ID: " + orderId);
    }

    public OrderNotFoundException(String message) {
        super(message);
    }
}
