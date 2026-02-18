package edu.comillas.icai.pista_padel.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    private Long idUsuario;
    private Long idPista;

    private LocalDate fechaReserva;
    private LocalTime horaInicio;
    private int duracionMinutos;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estado;

    private LocalDateTime fechaCreacion;

    public Reserva() {
    }

    public Reserva(Long idReserva,
                   Long idUsuario,
                   Long idPista,
                   LocalDate fechaReserva,
                   LocalTime horaInicio,
                   int duracionMinutos,
                   EstadoReserva estado,
                   LocalDateTime fechaCreacion) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idPista = idPista;
        this.fechaReserva = fechaReserva;
        this.horaInicio = horaInicio;
        this.duracionMinutos = duracionMinutos;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPista() {
        return idPista;
    }

    public void setIdPista(Long idPista) {
        this.idPista = idPista;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public LocalTime getHoraFin() {
        if (horaInicio == null) return null;
        return horaInicio.plusMinutes(duracionMinutos);
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(idReserva, reserva.idReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", idUsuario=" + idUsuario +
                ", idPista=" + idPista +
                ", fechaReserva=" + fechaReserva +
                ", horaInicio=" + horaInicio +
                ", duracionMinutos=" + duracionMinutos +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
