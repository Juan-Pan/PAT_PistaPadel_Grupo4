package edu.comillas.icai.pista_padel.seguridad;

import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.entity.Rol;
import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.repositorio.RepositorioPistas;
import edu.comillas.icai.pista_padel.repositorio.RepositorioUsuarios;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DatosIniciales {

    RepositorioPistas pistasRepositorio;

    @Bean
    public CommandLineRunner seedAdmin(RepositorioUsuarios repositorioUsuarios, PasswordEncoder codificadorPassword) {
        return args -> {
            String emailAdmin = "admin@admin.com";
            if (repositorioUsuarios.buscarPorEmail(emailAdmin).isPresent()) return;

            Usuario admin = new Usuario();
            admin.setNombre("Admin");
            admin.setApellidos("Del Sistema");
            admin.setEmail(emailAdmin);
            admin.setTelefono("000000000");
            admin.setRol(Rol.ADMIN);
            admin.setActivo(true);
            admin.setFechaRegistro(LocalDateTime.now());
            admin.setPassword(codificadorPassword.encode("admin"));

            pistasRepositorio.guardar(
                    new Pista(
                            null,
                            "Pista 1",
                            "Interior",
                            20.0,
                            true,
                            LocalDateTime.now()
                    )
            );


            repositorioUsuarios.guardar(admin);
        };
    }
}
