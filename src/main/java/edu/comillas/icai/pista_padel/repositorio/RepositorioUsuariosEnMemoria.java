package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RepositorioUsuariosEnMemoria implements RepositorioUsuariosMemoria {

    private final ConcurrentHashMap<Long, Usuario> usuarios = new ConcurrentHashMap<>();
    private final AtomicLong contador = new AtomicLong(0);

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getIdUsuario() == null) {
            usuario.setIdUsuario(contador.incrementAndGet());
        }
        usuarios.put(usuario.getIdUsuario(), usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long idUsuario) {
        return Optional.ofNullable(usuarios.get(idUsuario));
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        if (email == null) return Optional.empty();
        for (Usuario u : usuarios.values()) {
            if (email.equalsIgnoreCase(u.getEmail())) return Optional.of(u);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public void eliminar(Long idUsuario) {
        usuarios.remove(idUsuario);
    }
}
