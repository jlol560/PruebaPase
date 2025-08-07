package com.jlo.prueba.pase.exceptions.drivers;

public class LicenseNumberException extends RuntimeException {

    public LicenseNumberException(String licenseNumber) {
        super("El número de licencia " + licenseNumber + " debe de ser unico.");
    }

    public LicenseNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
