package edu.comillas.icai.pista_padel.controller;

import edu.comillas.icai.pista_padel.entity.Reserva;
import edu.comillas.icai.pista_padel.entity.Rol;
import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.servicio.ServicioDisponibilidad;
import edu.comillas.icai.pista_padel.servicio.ServicioReservas;
import edu.comillas.icai.pista_padel.repositorio.RepositorioUsuarios;
import edu.comillas.icai.pista_padel.repositorio.RepositorioPistas;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/pistaPadel")
public class ControladorReservas {

    private final ServicioReservas servicioReservas;
    private final ServicioDisponibilidad servicioDisponibilidad;
    private final RepositorioUsuarios repositorioUsuarios;
    private final RepositorioPistas repositorioPistas;


    public ControladorReservas(ServicioReservas servicioReservas,
                               ServicioDisponibilidad servicioDisponibilidad,
                               RepositorioUsuarios repositorioUsuarios,
                               RepositorioPistas repositorioPistas) {
        this.servicioReservas = servicioReservas;
        this.servicioDisponibilidad = servicioDisponibilidad;
        this.repositorioUsuarios = repositorioUsuarios;
        this.repositorioPistas = repositorioPistas;
    }


    private Usuario obtenerUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return repositorioUsuarios.buscarPorEmail(email).orElseThrow();
    }

    @PostMapping("/reservations")
    public Reserva crear(@RequestBody Reserva reserva) {

        Usuario usuario = obtenerUsuarioActual();

        return servicioReservas.crearReserva(
                usuario.getIdUsuario(),
                reserva.getIdPista(),
                reserva.getFechaReserva(),
                reserva.getHoraInicio(),
                reserva.getDuracionMinutos()
        );
    }

    @GetMapping("/reservations")
    public List<Reserva> listar() {

        Usuario usuario = obtenerUsuarioActual();

        if (usuario.getRol() == Rol.ADMIN) {
            return servicioReservas.listarTodas();
        }

        return servicioReservas.listarReservasUsuario(usuario.getIdUsuario());
    }

    @GetMapping("/reservations/{id}")
    public Reserva obtenerPorId(@PathVariable Long id) {

        Usuario usuario = obtenerUsuarioActual();

        Reserva reserva = servicioReservas.obtenerPorId(id);

        if (usuario.getRol() != Rol.ADMIN &&
                !reserva.getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.FORBIDDEN,
                    "No autorizado"
            );
        }

        return reserva;
    }

    @PatchMapping("/reservations/{id}")
    public Reserva modificar(@PathVariable Long id,
                             @RequestBody Reserva datos) {

        Usuario usuario = obtenerUsuarioActual();

        return servicioReservas.modificarReserva(
                id,
                usuario.getIdUsuario(),
                usuario.getRol() == Rol.ADMIN,
                datos.getFechaReserva(),
                datos.getHoraInicio(),
                datos.getDuracionMinutos()
        );
    }

    @DeleteMapping("/reservations/{id}")
    public void cancelar(@PathVariable Long id) {

        Usuario usuario = obtenerUsuarioActual();

        servicioReservas.cancelarReserva(
                id,
                usuario.getIdUsuario(),
                usuario.getRol() == Rol.ADMIN
        );
    }

    @GetMapping("/courts/{id}/availability")
    public List<String> disponibilidad(@PathVariable Long id,
                                       @RequestParam LocalDate date) {

        return servicioDisponibilidad.calcularDisponibilidad(id, date);
    }

    @GetMapping("/admin/reservations")
    public List<Reserva> listarAdmin(
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) Long courtId,
            @RequestParam(required = false) Long userId) {

        Usuario usuario = obtenerUsuarioActual();

        if (usuario.getRol() != Rol.ADMIN) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.FORBIDDEN,
                    "Solo ADMIN"
            );
        }

        List<Reserva> reservas = servicioReservas.listarTodas();

        if (date != null) {
            reservas.removeIf(r -> !date.equals(r.getFechaReserva()));
        }
        if (courtId != null) {
            reservas.removeIf(r -> !courtId.equals(r.getIdPista()));
        }
        if (userId != null) {
            reservas.removeIf(r -> !userId.equals(r.getIdUsuario()));
        }

        return reservas;
    }

    @GetMapping("/availability")
    public java.util.Map<Long, java.util.List<String>> disponibilidadGlobal(
            @RequestParam LocalDate date) {

        java.util.Map<Long, java.util.List<String>> resultado = new java.util.HashMap<>();

        List<edu.comillas.icai.pista_padel.entity.Pista> pistas =
                repositorioPistas.listar();

        for (edu.comillas.icai.pista_padel.entity.Pista pista : pistas) {
            resultado.put(
                    pista.getIdPista(),
                    servicioDisponibilidad.calcularDisponibilidad(pista.getIdPista(), date)
            );
        }

        return resultado;
    }


}
