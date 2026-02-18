package edu.comillas.icai.pista_padel.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // 409
public class NombrePistaDuplicadoException extends RuntimeException {
    public NombrePistaDuplicadoException(String nombre) {
        super("Ya existe una pista con nombre: " + nombre);
    }
}
