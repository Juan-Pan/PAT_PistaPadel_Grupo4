package edu.comillas.icai.pista_padel.dto;

public record UsuarioResponse(
        Long id,
        String email,
        String nombre,
        String rol
) {}