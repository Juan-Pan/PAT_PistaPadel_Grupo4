package edu.comillas.icai.pista_padel.dto;

public record PatchPistaRequest(
        String nombre,
        String ubicacion,
        Double precioHora,
        Boolean activa
) {}
