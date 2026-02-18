package edu.comillas.icai.pista_padel.controlador;

import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.servicio.ServicioUsuarios;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pistaPadel/users")
public class ControladorUsuarios {

    private final ServicioUsuarios servicioUsuarios;

    public ControladorUsuarios(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }

    // Regla: solo ADMIN lista usuarios
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> listar() {
        return servicioUsuarios.listar();
    }

    // (Recomendado) ADMIN o dueño puede ver
    @PreAuthorize("hasRole('ADMIN') or @servicioUsuarios.esDueno(#id, authentication.name)")
    @GetMapping("/{id}")
    public Usuario obtener(@PathVariable Long id) {
        return servicioUsuarios.obtenerPorId(id);
    }

    // Regla: solo dueño o ADMIN puede modificar
    @PreAuthorize("hasRole('ADMIN') or @servicioUsuarios.esDueno(#id, authentication.name)")
    @PatchMapping("/{id}")
    public Usuario patch(@PathVariable Long id, @RequestBody Usuario cambios) {
        return servicioUsuarios.patch(id, cambios);
    }
}
