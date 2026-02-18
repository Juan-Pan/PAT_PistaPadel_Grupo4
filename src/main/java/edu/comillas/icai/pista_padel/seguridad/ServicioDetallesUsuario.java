package edu.comillas.icai.pista_padel.seguridad;


import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.repositorio.RepositorioUsuarios;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDetallesUsuario implements UserDetailsService {

    private final RepositorioUsuarios repositorioUsuarios;

    public ServicioDetallesUsuario(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repositorioUsuarios.buscarPorEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        boolean enabled = usuario.isActivo();

        // Spring Security espera roles como "ROLE_ADMIN", "ROLE_USER"
        String rol = "ROLE_" + usuario.getRol().name();

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                enabled,
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(rol))
        );
    }
}
