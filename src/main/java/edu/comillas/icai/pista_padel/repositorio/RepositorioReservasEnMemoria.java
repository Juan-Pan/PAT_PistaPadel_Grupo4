package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entity.EstadoReserva;
import edu.comillas.icai.gitt.pat.spring.jpa.entity.Reserva;
import edu.comillas.icai.pista_padel.entity.EstadoReserva;
import edu.comillas.icai.pista_padel.entity.Reserva;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RepositorioReservasEnMemoria implements RepositorioReservas {

    private final ConcurrentHashMap<Long, Reserva> reservas = new ConcurrentHashMap<>();
    private final AtomicLong contador = new AtomicLong(0);

    @Override
    public Reserva guardar(Reserva reserva) {
        if (reserva.getIdReserva() == null) {
            reserva.setIdReserva(contador.incrementAndGet());
        }
        reservas.put(reserva.getIdReserva(), reserva);
        return reserva;
    }

    @Override
    public Optional<Reserva> buscarPorId(Long idReserva) {
        return Optional.ofNullable(reservas.get(idReserva));
    }

    @Override
    public List<Reserva> listar() {
        return new ArrayList<>(reservas.values());
    }

    @Override
    public List<Reserva> listarPorUsuario(Long idUsuario) {
        List<Reserva> out = new ArrayList<>();
        for (Reserva r : reservas.values()) {
            if (idUsuario != null && idUsuario.equals(r.getIdUsuario())) {
                out.add(r);
            }
        }
        return out;
    }

    @Override
    public List<Reserva> listarPorPistaYFecha(Long idPista, LocalDate fechaReserva) {
        List<Reserva> out = new ArrayList<>();
        for (Reserva r : reservas.values()) {
            if (idPista != null && idPista.equals(r.getIdPista())
                    && fechaReserva != null && fechaReserva.equals(r.getFechaReserva())) {
                out.add(r);
            }
        }
        return out;
    }

    @Override
    public List<Reserva> buscarSolapadas(Long idPista, LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin) {
        List<Reserva> out = new ArrayList<>();
        for (Reserva r : reservas.values()) {
            if (r.getEstado() != EstadoReserva.ACTIVA) continue;
            if (idPista == null || !idPista.equals(r.getIdPista())) continue;
            if (fechaReserva == null || !fechaReserva.equals(r.getFechaReserva())) continue;

            LocalTime inicioExistente = r.getHoraInicio();
            LocalTime finExistente = r.getHoraFin();
            if (inicioExistente == null || finExistente == null || horaInicio == null || horaFin == null) continue;

            // solape: inicio1 < fin2 && inicio2 < fin1
            boolean solapa = horaInicio.isBefore(finExistente) && inicioExistente.isBefore(horaFin);
            if (solapa) out.add(r);
        }
        return out;
    }
}
