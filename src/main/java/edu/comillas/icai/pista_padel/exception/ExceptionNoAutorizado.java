package edu.comillas.icai.pista_padel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED) // Devuelve un 401
public class ExceptionNoAutorizado extends RuntimeException {
    public ExceptionNoAutorizado(String mensaje) {
        super(mensaje);
    }
}