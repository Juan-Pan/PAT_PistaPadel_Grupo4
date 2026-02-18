package edu.comillas.icai.pista_padel.controlador;

import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.servicio.ServicioPista;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pistaPadel/courts")
public class ControladorPistas {

    private final ServicioPista servicioPista;

    public ControladorPistas(ServicioPista servicioPista) {
        this.servicioPista = servicioPista;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public Pista crear(@RequestBody Pista req) {
        return servicioPista.crearPista(req);
    }

    @GetMapping
    public List<Pista> listar() {
        return servicioPista.listarPistas();
    }

    @GetMapping("/{id}")
    public Pista obtener(@PathVariable Long id) {
        return servicioPista.buscarPista(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Pista actualizar(@PathVariable Long id, @RequestBody Pista cambios) {
        return servicioPista.actualizarPista(id, cambios);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
        servicioPista.borrarPista(id);
    }
}
