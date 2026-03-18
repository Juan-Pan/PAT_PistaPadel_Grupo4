package edu.comillas.icai.pista_padel;

import edu.comillas.icai.pista_padel.controller.PistaController;
import edu.comillas.icai.pista_padel.entity.Pista;
import edu.comillas.icai.pista_padel.servicio.ServicioPista;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.WebContentGenerator;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Comprobamos que el número de elementos devueltos en el JSON
// coincide con el tamaño de la lista simulada por el servicio
@WebMvcTest(PistaController.class)
public class PistaControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ServicioPista servicioPista;
    @Autowired
    private WebContentGenerator webContentGenerator;

    @Test
    @WithMockUser(username = "ana@test.com", roles = "USER")
    void listarPistas_devuelve200_y_lista_json() throws Exception {

        // Creamos dos pistas simuladas que devolverá el servicio
        Pista pista1 = new Pista();
        pista1.setIdPista(1L);
        pista1.setNombre("Central");
        pista1.setUbicacion("Madrid");
        pista1.setPrecioHora(20.0);

        Pista pista2 = new Pista();
        pista2.setIdPista(2L);
        pista2.setNombre("Exterior");
        pista2.setUbicacion("Madrid");
        pista2.setPrecioHora(18.0);

        // Guardamos las pistas en una lista
        List<Pista> pistas = List.of(pista1, pista2);

        // Simulamos la respuesta del servicio
        when(servicioPista.listarPistas()).thenReturn(pistas);

        // Simulamos una petición GET al endpoint de listado de pistas
        mockMvc.perform(get("/pistaPadel/courts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(pistas.size()))
                .andExpect(jsonPath("$[0].nombre").value("Central"))
                .andExpect(jsonPath("$[1].nombre").value("Exterior"));
    }
}