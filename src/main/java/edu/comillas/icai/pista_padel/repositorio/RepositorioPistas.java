package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Pista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioPistas extends JpaRepository<Pista, Long> {

    boolean existsByNombre(String nombre);

    Optional<Pista> findByNombre(String nombre);
}
