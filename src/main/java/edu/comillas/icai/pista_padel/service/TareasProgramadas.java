package edu.comillas.icai.pista_padel.service;

import edu.comillas.icai.pista_padel.repositorio.RepositorioUsuarios;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TareasProgramadas {

    private static final Logger log = LoggerFactory.getLogger(TareasProgramadas.class);
    private final RepositorioUsuarios repositorioUsuarios;

    public TareasProgramadas(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    // Tarea 1: Recordatorio diario a las 02:00 AM
    // Formato Cron: "0 0 2 * * ?" (Segundo 0, Minuto 0, Hora 2, Todos los días)
    @Scheduled(cron = "0 0 2 * * ?")
    public void enviarRecordatorios() {
        log.info("Iniciando envío de recordatorios diarios...");
        repositorioUsuarios.listar().forEach(usuario -> {
            // Simulamos el envío del email
            log.info("Enviando recordatorio a {}", usuario.getEmail());
        });
    }

    // Tarea 2: Resumen mensual el día 1 de cada mes a las 00:00 AM
    @Scheduled(cron = "0 0 0 1 * ?")
    public void enviarResumenMensual() {
        log.info("Generando y enviando resumen mensual a los administradores...");
        // Aquí iría la lógica para contar reservas del mes anterior
    }
}