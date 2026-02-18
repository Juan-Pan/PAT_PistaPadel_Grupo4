package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Usuario;

import java.util.Optional;

public interface RepositorioUsuariosMemoria extends RepositorioBase<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}

