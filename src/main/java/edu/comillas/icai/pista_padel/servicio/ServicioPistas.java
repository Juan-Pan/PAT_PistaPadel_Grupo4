package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.repositorio.RepositorioPistas;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPistas {

    private final RepositorioPistas repositorioPistas;

    public ServicioPistas(RepositorioPistas repositorioPistas) {
        this.repositorioPistas = repositorioPistas;
    }

    public Pista crearPista(Pista pista) {
        if (pista.getNombre() != null && repositorioPistas.existsByNombre(pista.getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una pista con el nombre: " + pista.getNombre()
            );
        }

        pista.setActiva(true);
        if (pista.getFechaAlta() == null) {
            pista.setFechaAlta(LocalDateTime.now());
        }

        return repositorioPistas.save(pista);
    }

    public Optional<Pista> obtenerPista(Long idPista) {
        return repositorioPistas.findById(idPista);
    }

    public List<Pista> listarPistas() {
        return repositorioPistas.findAll();
    }

    public Pista actualizarPista(Long idPista, Pista pistaActualizada) {
        Pista pistaExistente = repositorioPistas.findById(idPista)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró la pista con id: " + idPista
                ));

        if (pistaActualizada.getNombre() != null &&
            !pistaActualizada.getNombre().equals(pistaExistente.getNombre())) {
            if (repositorioPistas.existsByNombre(pistaActualizada.getNombre())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Ya existe otra pista con el nombre: " + pistaActualizada.getNombre()
                );
            }
            pistaExistente.setNombre(pistaActualizada.getNombre());
        }

        if (pistaActualizada.getUbicacion() != null) {
            pistaExistente.setUbicacion(pistaActualizada.getUbicacion());
        }

        if (pistaActualizada.getPrecioHora() > 0) {
            pistaExistente.setPrecioHora(pistaActualizada.getPrecioHora());
        }

        pistaExistente.setActiva(pistaActualizada.isActiva());

        return repositorioPistas.save(pistaExistente);
    }

    public void borrarPista(Long idPista) {
        Pista pista = repositorioPistas.findById(idPista)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No se encontró la pista con id: " + idPista
                ));

        pista.setActiva(false);
        repositorioPistas.save(pista);
    }
}
