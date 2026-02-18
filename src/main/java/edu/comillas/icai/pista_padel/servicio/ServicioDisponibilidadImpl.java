package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.entity.Reserva;
import edu.comillas.icai.pista_padel.repositorio.RepositorioReservas;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ServicioDisponibilidadImpl implements ServicioDisponibilidad {

    private final RepositorioReservas repositorioReservas;

    private static final LocalTime HORA_APERTURA = LocalTime.of(8,0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(22,0);

    public ServicioDisponibilidadImpl(RepositorioReservas repositorioReservas) {
        this.repositorioReservas = repositorioReservas;
    }

    @Override
    public List<String> calcularDisponibilidad(Long idPista, LocalDate fecha) {

        List<Reserva> reservas =
                repositorioReservas.listarPorPistaYFecha(idPista, fecha);

        reservas.sort(Comparator.comparing(Reserva::getHoraInicio));

        List<String> disponibilidad = new ArrayList<>();

        LocalTime cursor = HORA_APERTURA;

        for (Reserva r : reservas) {

            if (r.getHoraInicio().isAfter(cursor)) {
                disponibilidad.add(cursor + " - " + r.getHoraInicio());
            }
            cursor = r.getHoraFin();
        }

        if (cursor.isBefore(HORA_CIERRE)) {
            disponibilidad.add(cursor + " - " + HORA_CIERRE);
        }

        return disponibilidad;
    }
}
