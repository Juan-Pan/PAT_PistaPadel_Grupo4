package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RepositorioReservasMemoria extends RepositorioBase<Reserva, Long> {
    List<Reserva> findByIdUsuario(Long idUsuario);

    List<Reserva> findByIdPistaAndFechaReserva(Long idPista, LocalDate fechaReserva);

    List<Reserva> buscarSolapadas(Long idPista, LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin);
}

