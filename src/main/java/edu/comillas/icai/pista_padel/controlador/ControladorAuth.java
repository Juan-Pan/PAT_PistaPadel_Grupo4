package edu.comillas.icai.pista_padel.controlador;

import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.servicio.ServicioUsuarios;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pistaPadel/auth")
public class ControladorAuth {

    private final ServicioUsuarios servicioUsuarios;

    public ControladorAuth(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }

    // Público (permitAll en ConfiguracionSeguridad)
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Usuario register(@RequestBody Usuario req) {
        return servicioUsuarios.registrar(req);
    }

    // Requiere auth (por ConfiguracionSeguridad)
    @GetMapping("/me")
    public Usuario me(Authentication authentication) {
        return servicioUsuarios.obtenerPorEmail(authentication.getName());
    }
}

