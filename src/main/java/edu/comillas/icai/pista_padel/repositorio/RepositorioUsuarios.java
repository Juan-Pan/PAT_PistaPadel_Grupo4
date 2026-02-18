package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface RepositorioUsuarios {
    Usuario guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long idUsuario);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> listar();
    void eliminar(Long idUsuario);
}
