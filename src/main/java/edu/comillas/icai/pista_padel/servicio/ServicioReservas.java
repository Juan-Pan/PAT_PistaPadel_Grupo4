package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.entity.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ServicioReservas {

    Reserva crearReserva(Long idUsuario,
                         Long idPista,
                         LocalDate fecha,
                         LocalTime horaInicio,
                         int duracionMinutos);

    List<Reserva> listarReservasUsuario(Long idUsuario);

    List<Reserva> listarTodas();

    Reserva obtenerPorId(Long idReserva);

    void cancelarReserva(Long idReserva, Long idUsuarioSolicitante, boolean esAdmin);

    Reserva modificarReserva(Long idReserva,
                             Long idUsuarioSolicitante,
                             boolean esAdmin,
                             LocalDate nuevaFecha,
                             LocalTime nuevaHoraInicio,
                             int nuevaDuracion);
}
