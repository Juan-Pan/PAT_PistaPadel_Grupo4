package edu.comillas.icai.pista_padel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Devuelve un 404
public class ExceptionNoEncontrado extends RuntimeException {
    public ExceptionNoEncontrado(String mensaje) {
        super(mensaje);
    }
}