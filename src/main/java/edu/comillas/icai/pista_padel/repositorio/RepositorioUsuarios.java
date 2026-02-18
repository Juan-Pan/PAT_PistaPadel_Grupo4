package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuarios extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
