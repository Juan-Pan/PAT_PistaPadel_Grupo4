package edu.comillas.icai.pista_padel.servicio;

import edu.comillas.icai.pista_padel.entity.Rol;
import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.error.EmailDuplicadoException;
import edu.comillas.icai.pista_padel.repositorio.RepositorioUsuarios;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import edu.comillas.icai.pista_padel.error.NoEncontradoException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("servicioUsuarios") // para usarlo en @PreAuthorize
public class ServicioUsuarios {

    private final RepositorioUsuarios repositorioUsuarios;
    private final PasswordEncoder passwordEncoder;

    public ServicioUsuarios(RepositorioUsuarios repositorioUsuarios, PasswordEncoder passwordEncoder) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.passwordEncoder = passwordEncoder;
    }

    // POST /auth/register
    // Reglas: email único (409) + rol default USER + password BCrypt + activo=true + fechaRegistro=now
    public Usuario registrar(Usuario req) {
        String email = req.getEmail();

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email requerido");
        }

        if (repositorioUsuarios.buscarPorEmail(email).isPresent()) {
            throw new EmailDuplicadoException(email);
        }

        Usuario u = new Usuario();
        u.setNombre(req.getNombre());
        u.setApellidos(req.getApellidos());
        u.setEmail(email);
        u.setTelefono(req.getTelefono());

        u.setRol(Rol.USER); // default USER
        u.setActivo(true);
        u.setFechaRegistro(LocalDateTime.now());

        // password cifrada
        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password requerida");
        }
        u.setPassword(passwordEncoder.encode(req.getPassword()));

        return repositorioUsuarios.guardar(u);
    }

    // GET /users (ADMIN)
    public List<Usuario> listar() {
        return repositorioUsuarios.listar();
    }


    // GET /users/{id}
    public Usuario obtenerPorId(Long id) {
        return repositorioUsuarios.buscarPorId(id)
                .orElseThrow(() -> new NoEncontradoException("Usuario no encontrado: " + id));
        // Si queréis 404 limpio, luego hacemos NoEncontradoException con @ResponseStatus(HttpStatus.NOT_FOUND)
    }

    // GET /auth/me
    public Usuario obtenerPorEmail(String email) {
        return repositorioUsuarios.buscarPorEmail(email)
                .orElseThrow(() -> new NoEncontradoException("Usuario no encontrado: " + email));
    }

    // PATCH /users/{id}
    // Reglas: email único (409) si cambia + permite actualizar nombre/apellidos/email/telefono/password
    public Usuario patch(Long id, Usuario cambios) {
        Usuario u = obtenerPorId(id);

        // email
        if (cambios.getEmail() != null && !cambios.getEmail().isBlank()
                && !cambios.getEmail().equalsIgnoreCase(u.getEmail())) {

            Optional<Usuario> existente = repositorioUsuarios.buscarPorEmail(cambios.getEmail());
            if (existente.isPresent() && !existente.get().getIdUsuario().equals(u.getIdUsuario())) {
                throw new EmailDuplicadoException(cambios.getEmail());
            }
            u.setEmail(cambios.getEmail());
        }

        if (cambios.getNombre() != null) u.setNombre(cambios.getNombre());
        if (cambios.getApellidos() != null) u.setApellidos(cambios.getApellidos());
        if (cambios.getTelefono() != null) u.setTelefono(cambios.getTelefono());

        if (cambios.getPassword() != null && !cambios.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(cambios.getPassword()));
        }

        // NO dejamos cambiar rol desde PATCH (mejor práctica)
        // NO dejamos cambiar fechaRegistro / activo desde PATCH (salvo que lo pidan)

        return repositorioUsuarios.guardar(u);
    }

    // Para @PreAuthorize: dueño si auth email == email del usuario id
    public boolean esDueno(Long id, String emailAuth) {
        return repositorioUsuarios.buscarPorId(id)
                .map(u -> u.getEmail().equalsIgnoreCase(emailAuth))
                .orElse(false);
    }
}

