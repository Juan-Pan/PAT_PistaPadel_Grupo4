package edu.comillas.icai.pista_padel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Devuelve un 409
public class ExceptionConflicto extends RuntimeException {
    public ExceptionConflicto(String mensaje) {
        super(mensaje);
    }
}