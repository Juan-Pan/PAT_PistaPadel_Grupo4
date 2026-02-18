package edu.comillas.icai.pista_padel.repositorio;

import edu.comillas.icai.pista_padel.entity.Pista;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RepositorioPistasEnMemoria implements RepositorioPistasMemoria {

    private final ConcurrentHashMap<Long, Pista> pistas = new ConcurrentHashMap<>();
    private final AtomicLong contador = new AtomicLong(0);

    @Override
    public Pista guardar(Pista pista) {
        if (pista.getIdPista() == null) {
            pista.setIdPista(contador.incrementAndGet());
        }
        pistas.put(pista.getIdPista(), pista);
        return pista;
    }

    @Override
    public Optional<Pista> buscarPorId(Long idPista) {
        return Optional.ofNullable(pistas.get(idPista));
    }

    @Override
    public Optional<Pista> findByNombre(String nombre) {
        if (nombre == null) return Optional.empty();
        for (Pista p : pistas.values()) {
            if (nombre.equalsIgnoreCase(p.getNombre())) return Optional.of(p);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Pista> buscarPorNombre(String nombre) {
        return findByNombre(nombre);
    }

    @Override
    public List<Pista> listar() {
        return new ArrayList<>(pistas.values());
    }

    @Override
    public void eliminar(Long idPista) {
        pistas.remove(idPista);
    }
}
