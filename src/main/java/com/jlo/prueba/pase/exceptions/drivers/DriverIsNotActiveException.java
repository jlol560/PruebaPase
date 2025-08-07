package com.jlo.prueba.pase.exceptions.drivers;

import java.util.UUID;

public class DriverIsNotActiveException extends RuntimeException {

    public DriverIsNotActiveException(String message) {
        super(message);
    }

    public DriverIsNotActiveException(UUID driverId) {
        super("El conductor "+driverId+" no está activo.");
    }
}
