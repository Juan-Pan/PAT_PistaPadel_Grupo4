package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.repositorio.RepositorioPistas;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioPista {

    private final RepositorioPistas repositorioPistas;

    public ServicioPista(RepositorioPistas repositorioPistas) {
        this.repositorioPistas = repositorioPistas;
    }

    public Pista crearPista(Pista p) {
        if (repositorioPistas.buscarPorNombre(p.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        p.setActiva(true);
        p.setFechaAlta(LocalDateTime.now());
        return repositorioPistas.guardar(p);
    }

    public List<Pista> listarPistas() {
        return repositorioPistas.listar();
    }

    public Pista buscarPista(Long id) {
        return repositorioPistas.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Pista actualizarPista(Long id, Pista p) {
        Pista pistaExistente = buscarPista(id);

        if (p.getNombre() != null && !p.getNombre().equals(pistaExistente.getNombre())) {
            if (repositorioPistas.buscarPorNombre(p.getNombre()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
            pistaExistente.setNombre(p.getNombre());
        }

        if (p.getUbicacion() != null) {
            pistaExistente.setUbicacion(p.getUbicacion());
        }

        if (p.getPrecioHora() > 0) {
            pistaExistente.setPrecioHora(p.getPrecioHora());
        }

        return repositorioPistas.guardar(pistaExistente);
    }

    public void borrarPista(Long id) {
        Pista pista = buscarPista(id);
        pista.setActiva(false);
        repositorioPistas.guardar(pista);
    }
}
