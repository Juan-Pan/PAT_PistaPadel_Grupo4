package edu.comillas.icai.pista_padel.controller;

import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.servicio.ServicioPistas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pistaPadel/courts")
public class ControladorPistas {

    private final ServicioPistas servicioPistas;

    public ControladorPistas(ServicioPistas servicioPistas) {
        this.servicioPistas = servicioPistas;
    }

    @GetMapping
    public ResponseEntity<List<Pista>> listarPistas() {
        List<Pista> pistas = servicioPistas.listarPistas();
        return ResponseEntity.ok(pistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pista> obtenerPista(@PathVariable Long id) {
        Optional<Pista> pista = servicioPistas.obtenerPista(id);
        return pista.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pista> crearPista(@RequestBody Pista pista) {
        Pista pistaCreada = servicioPistas.crearPista(pista);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pistaCreada);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pista> actualizarPista(@PathVariable Long id,
                                                  @RequestBody Pista pistaActualizada) {
        Pista pistaActual = servicioPistas.actualizarPista(id, pistaActualizada);
        return ResponseEntity.ok(pistaActual);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> borrarPista(@PathVariable Long id) {
        servicioPistas.borrarPista(id);
        return ResponseEntity.noContent().build();
    }
}
