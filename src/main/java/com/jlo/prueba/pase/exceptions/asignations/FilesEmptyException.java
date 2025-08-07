package com.jlo.prueba.pase.exceptions.asignations;

public class FilesEmptyException extends RuntimeException {

    public FilesEmptyException(String message) {
        super(message);
    }

    public FilesEmptyException() {
        super("Files cannot be empty");
    }
}
