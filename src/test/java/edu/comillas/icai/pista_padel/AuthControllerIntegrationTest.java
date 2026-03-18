package edu.comillas.icai.pista_padel;

import edu.comillas.icai.pista_padel.controller.AuthController;
import edu.comillas.icai.pista_padel.entity.Usuario;
import edu.comillas.icai.pista_padel.servicio.ServicioUsuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Este test comprueba que el endpoint de registro del AuthController procesa correctamente
// una petición POST con los datos de un usuario, devuelve el estado HTTP 201 Created
// y responde con el usuario creado en formato JSON.

public class AuthControllerIntegrationTest {

    private MockMvc mockMvc;
    private ServicioUsuarios servicioUsuarios;

    @BeforeEach
    void setUp() {
        // Creamos un mock del servicio sin necesitar su constructor real
        servicioUsuarios = Mockito.mock(ServicioUsuarios.class);

        // Creamos el controlador manualmente
        AuthController authController = new AuthController(servicioUsuarios);

        // Montamos MockMvc para probar peticiones HTTP sin levantar todo Spring
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void register_devuelve201_y_usuario_creado() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNombre("Ana");
        usuario.setEmail("ana@test.com");
        usuario.setActivo(true);

        when(servicioUsuarios.registrar(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/pistaPadel/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "A",
                                  "email": "ana@test.com",
                                  "password": "1234",
                                  "activo": true
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idUsuario").value(1))
                .andExpect(jsonPath("$.nombre").value("Ana"))
                .andExpect(jsonPath("$.email").value("ana@test.com"));
    }
}