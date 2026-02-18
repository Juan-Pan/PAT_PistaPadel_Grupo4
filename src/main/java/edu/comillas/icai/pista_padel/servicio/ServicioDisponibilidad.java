package edu.comillas.icai.pista_padel.servicio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioDisponibilidad {

    List<String> calcularDisponibilidad(Long idPista, LocalDate fecha);
}
