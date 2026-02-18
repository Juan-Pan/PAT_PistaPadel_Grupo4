package edu.comillas.icai.pista_padel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pistas")
public class Pista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPista;

    @Column(unique = true, nullable = false)
    private String nombre;

    private String ubicacion;
    private double precioHora;
    private boolean activa;
    private LocalDateTime fechaAlta;

    public Pista() {
        this.activa = true;
        this.fechaAlta = LocalDateTime.now();
    }

    public Pista(Long idPista,
                 String nombre,
                 String ubicacion,
                 double precioHora,
                 boolean activa,
                 LocalDateTime fechaAlta) {
        this.idPista = idPista;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precioHora = precioHora;
        this.activa = activa;
        this.fechaAlta = fechaAlta;
    }

    public Pista(String nombre,
                 String ubicacion,
                 double precioHora) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precioHora = precioHora;
        this.activa = true;
        this.fechaAlta = LocalDateTime.now();
    }

    public Long getIdPista() {
        return idPista;
    }

    public void setIdPista(Long idPista) {
        this.idPista = idPista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "idPista=" + idPista +
                ", nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", precioHora=" + precioHora +
                ", activa=" + activa +
                ", fechaAlta=" + fechaAlta +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pista)) return false;
        Pista pista = (Pista) o;
        return Objects.equals(idPista, pista.idPista);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPista);
    }
}
