package edu.comillas.icai.pista_padel.controlador;

import edu.comillas.icai.pista_padel.dto.PatchPistaRequest;
import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.servicio.ServicioPista;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pistaPadel/pistas")
public class ControladorPistas {

    private final ServicioPista servicioPista;

    public ControladorPistas(ServicioPista servicioPista) {
        this.servicioPista = servicioPista;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pista crear(@RequestBody Pista req) {
        return servicioPista.crear(req);
    }

    @GetMapping
    public List<Pista> listar() {
        return servicioPista.listar();
    }

    @GetMapping("/{id}")
    public Pista obtener(@PathVariable Long id) {
        return servicioPista.obtener(id);
    }

    @PatchMapping("/{id}")
    public Pista patch(@PathVariable Long id, @RequestBody PatchPistaRequest cambios) {
        return servicioPista.patch(id, cambios);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        servicioPista.eliminar(id);
    }
}

