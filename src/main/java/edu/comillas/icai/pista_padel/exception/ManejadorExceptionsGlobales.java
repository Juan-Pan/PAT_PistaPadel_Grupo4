package edu.comillas.icai.pista_padel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Indica que esta clase captura errores de todos los controladores
public class ManejadorExceptionsGlobales {


    @ExceptionHandler(ExceptionNoEncontrado.class)
    public ResponseEntity<Object> manejarNoEncontrado(ExceptionNoEncontrado ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ExceptionConflicto.class)
    public ResponseEntity<Object> manejarConflicto(ExceptionConflicto ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExceptionNoAutorizado.class)
    public ResponseEntity<Object> manejarNoAutorizado(ExceptionNoAutorizado ex) {
        return crearRespuesta(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // Método auxiliar para dar un formato JSON estándar al error
    private ResponseEntity<Object> crearRespuesta(String mensaje, HttpStatus status) {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("timestamp", LocalDateTime.now());
        cuerpo.put("mensaje", mensaje);
        cuerpo.put("estado", status.value());

        return new ResponseEntity<>(cuerpo, status);
    }
}