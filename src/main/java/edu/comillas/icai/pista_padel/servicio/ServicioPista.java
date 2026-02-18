package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.dto.PatchPistaRequest;
import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.error.NoEncontradoException;
import edu.comillas.icai.pista_padel.error.NombrePistaDuplicadoException;
import edu.comillas.icai.pista_padel.repositorio.RepositorioPistas;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioPista {

    private final RepositorioPistas repositorioPistas;

    public ServicioPista(RepositorioPistas repositorioPistas) {
        this.repositorioPistas = repositorioPistas;
    }

    // POST /courts
    public Pista crear(Pista req) {
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            throw new IllegalArgumentException("nombre requerido");
        }

        // nombre único -> 409
        if (repositorioPistas.buscarPorNombre(req.getNombre()).isPresent()) {
            throw new NombrePistaDuplicadoException(req.getNombre());
        }

        Pista p = new Pista();
        p.setNombre(req.getNombre());
        p.setUbicacion(req.getUbicacion());
        p.setPrecioHora(req.getPrecioHora());
        p.setActiva(req.isActiva());
        p.setFechaAlta(LocalDateTime.now());

        return repositorioPistas.guardar(p);
    }

    // GET /courts
    public List<Pista> listar() {
        return repositorioPistas.listar();
    }

    // GET /courts/{id}
    public Pista obtener(Long id) {
        return repositorioPistas.buscarPorId(id)
                .orElseThrow(() -> new NoEncontradoException("Pista no encontrada: " + id));
    }

    // PATCH /courts/{id}
    public Pista patch(Long id, PatchPistaRequest cambios) {
        Pista p = obtener(id);

        // nombre (si cambia, validar unicidad)
        if (cambios.nombre() != null && !cambios.nombre().isBlank()
                && !cambios.nombre().equalsIgnoreCase(p.getNombre())) {

            Optional<Pista> existente = repositorioPistas.buscarPorNombre(cambios.nombre());
            if (existente.isPresent() && !existente.get().getIdPista().equals(p.getIdPista())) {
                throw new NombrePistaDuplicadoException(cambios.nombre());
            }
            p.setNombre(cambios.nombre());
        }

        if (cambios.ubicacion() != null) {
            p.setUbicacion(cambios.ubicacion());
        }

        if (cambios.precioHora() != null) {
            p.setPrecioHora(cambios.precioHora());
        }

        if (cambios.activa() != null) {
            p.setActiva(cambios.activa());
        }

        return repositorioPistas.guardar(p);
    }

    // DELETE /courts/{id}
    public void eliminar(Long id) {
        // opcional: validar que existe para devolver 404 si no
        obtener(id);
        repositorioPistas.eliminar(id);
    }
}
