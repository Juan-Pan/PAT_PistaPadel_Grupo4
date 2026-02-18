package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.entity.*;
import edu.comillas.icai.pista_padel.repositorio.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ServicioReservasImpl implements ServicioReservas {

    private final RepositorioReservas repositorioReservas;
    private final RepositorioPistas repositorioPistas;
    private final RepositorioUsuarios repositorioUsuarios;

    private static final LocalTime HORA_APERTURA = LocalTime.of(8,0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(22,0);

    public ServicioReservasImpl(RepositorioReservas repositorioReservas,
                                RepositorioPistas repositorioPistas,
                                RepositorioUsuarios repositorioUsuarios) {
        this.repositorioReservas = repositorioReservas;
        this.repositorioPistas = repositorioPistas;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @Override
    public Reserva crearReserva(Long idUsuario, Long idPista,
                                LocalDate fecha, LocalTime horaInicio,
                                int duracionMinutos) {

        if (duracionMinutos <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duración inválida");

        Usuario usuario = repositorioUsuarios.buscarPorId(idUsuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Pista pista = repositorioPistas.buscarPorId(idPista)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pista no encontrada"));

        if (!pista.isActiva())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pista inactiva");

        LocalTime horaFin = horaInicio.plusMinutes(duracionMinutos);

        if (horaInicio.isBefore(HORA_APERTURA) || horaFin.isAfter(HORA_CIERRE))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fuera de horario");

        List<Reserva> solapadas =
                repositorioReservas.buscarSolapadas(idPista, fecha, horaInicio, horaFin);

        if (!solapadas.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slot ocupado");

        Reserva reserva = new Reserva(
                null,
                idUsuario,
                idPista,
                fecha,
                horaInicio,
                duracionMinutos,
                EstadoReserva.ACTIVA,
                LocalDateTime.now()
        );

        return repositorioReservas.guardar(reserva);
    }

    @Override
    public List<Reserva> listarReservasUsuario(Long idUsuario) {
        return repositorioReservas.listarPorUsuario(idUsuario);
    }

    @Override
    public List<Reserva> listarTodas() {
        return repositorioReservas.listar();
    }

    @Override
    public Reserva obtenerPorId(Long idReserva) {
        return repositorioReservas.buscarPorId(idReserva)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));
    }

    @Override
    public void cancelarReserva(Long idReserva, Long idUsuarioSolicitante, boolean esAdmin) {

        Reserva reserva = obtenerPorId(idReserva);

        if (!esAdmin && !reserva.getIdUsuario().equals(idUsuarioSolicitante))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");

        reserva.setEstado(EstadoReserva.CANCELADA);
        repositorioReservas.guardar(reserva);
    }

    @Override
    public Reserva modificarReserva(Long idReserva,
                                    Long idUsuarioSolicitante,
                                    boolean esAdmin,
                                    LocalDate nuevaFecha,
                                    LocalTime nuevaHoraInicio,
                                    int nuevaDuracion) {

        Reserva reserva = obtenerPorId(idReserva);

        if (!esAdmin && !reserva.getIdUsuario().equals(idUsuarioSolicitante))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No autorizado");

        LocalTime nuevaHoraFin = nuevaHoraInicio.plusMinutes(nuevaDuracion);

        List<Reserva> solapadas =
                repositorioReservas.buscarSolapadas(
                        reserva.getIdPista(),
                        nuevaFecha,
                        nuevaHoraInicio,
                        nuevaHoraFin
                );

        solapadas.removeIf(r -> r.getIdReserva().equals(idReserva));

        if (!solapadas.isEmpty())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nuevo slot ocupado");

        reserva.setFechaReserva(nuevaFecha);
        reserva.setHoraInicio(nuevaHoraInicio);
        reserva.setDuracionMinutos(nuevaDuracion);

        return repositorioReservas.guardar(reserva);
    }
}
