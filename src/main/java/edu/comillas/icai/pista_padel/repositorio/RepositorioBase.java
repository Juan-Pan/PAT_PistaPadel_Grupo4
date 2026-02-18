package edu.comillas.icai.pista_padel.repositorio;

import java.util.List;
import java.util.Optional;

public interface RepositorioBase<T, ID> {
    T guardar(T entidad);
    Optional<T> buscarPorId(ID id);
    List<T> listar();
    void eliminar(ID id);

    default Optional<T> buscarPorNombre(String nombre) {
        return Optional.empty();
    }
}



