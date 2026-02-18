package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Pista;

import java.util.Optional;

public interface RepositorioPistasMemoria extends RepositorioBase<Pista, Long> {
    Optional<Pista> findByNombre(String nombre);
}

