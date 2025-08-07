package com.jlo.prueba.pase.exceptions.drivers;

import java.util.UUID;

public class DriverNotFoundException extends RuntimeException {

    public DriverNotFoundException(UUID driverId) {
        super("No se encontró la orden con ID: " + driverId);
    }

    public DriverNotFoundException(String message) {
        super(message);
    }
}
