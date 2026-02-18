package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entity.Pista;
import edu.comillas.icai.pista_padel.entity.Pista;

import java.util.List;
import java.util.Optional;

public interface RepositorioPistas {
    Pista guardar(Pista pista);
    Optional<Pista> buscarPorId(Long idPista);
    Optional<Pista> buscarPorNombre(String nombre);
    List<Pista> listar();
    void eliminar(Long idPista);
}
