package edu.comillas.icai.pista_padel;

import edu.comillas.icai.pista_padel.controller.ReservaController;
import edu.comillas.icai.pista_padel.repositorio.RepositorioPistas;
import edu.comillas.icai.pista_padel.repositorio.RepositorioUsuarios;
import edu.comillas.icai.pista_padel.servicio.ServicioDisponibilidad;
import edu.comillas.icai.pista_padel.servicio.ServicioReservas;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Este test comprueba que el endpoint de disponibilidad de una pista responde correctamente.
// Se simula el comportamiento del servicio para devolver unas franjas horarias libres
// y se verifica que la petición GET devuelve estado 200 OK junto con la lista esperada en formato JSON.

@WebMvcTest(ReservaController.class)
public class ReservaControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    ServicioReservas servicioReservas;
    @MockitoBean
    ServicioDisponibilidad servicioDisponibilidad;
    @MockitoBean
    RepositorioUsuarios repositorioUsuarios;
    @MockitoBean
    RepositorioPistas repositorioPistas;

    @Test
    @WithMockUser(username = "ana@test.com", roles = "USER")
    void disponibilidadPista_devuelve200() throws Exception {
        when(servicioDisponibilidad.calcularDisponibilidad(1L, LocalDate.of(2026, 3, 20)))
                .thenReturn(List.of("10:00", "11:30"));

        mockMvc.perform(get("/pistaPadel/courts/1/availability")
                        .param("date", "2026-03-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("10:00"))
                .andExpect(jsonPath("$[1]").value("11:30"));
    }



}
