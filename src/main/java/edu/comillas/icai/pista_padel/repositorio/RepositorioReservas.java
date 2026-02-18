package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RepositorioReservas extends JpaRepository<Reserva, Long> {
    List<Reserva> findByIdUsuario(Long idUsuario);

    List<Reserva> findByIdPistaAndFechaReserva(Long idPista, LocalDate fechaReserva);

    List<Reserva> buscarSolapadas(Long idPista, LocalDate fechaReserva, LocalTime horaInicio, LocalTime horaFin);
}
