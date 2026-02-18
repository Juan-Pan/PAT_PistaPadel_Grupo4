package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entity.Reserva;
import edu.comillas.icai.pista_padel.entity.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface RepositorioReservas {
    Reserva guardar(Reserva reserva);
    Optional<Reserva> buscarPorId(Long idReserva);
    List<Reserva> listar();
    List<Reserva> listarPorUsuario(Long idUsuario);
    List<Reserva> listarPorPistaYFecha(Long idPista, LocalDate fechaReserva);

    /**
     * Devuelve reservas ACTIVAS que solapen con [horaInicio, horaFin) en esa pista+fecha.
     */
    List<Reserva> buscarSolapadas(Long idPista, LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin);
}
