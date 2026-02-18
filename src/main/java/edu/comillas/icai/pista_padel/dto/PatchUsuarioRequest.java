package edu.comillas.icai.pista_padel.dto;

import jakarta.validation.constraints.Email;

public record PatchUsuarioRequest(
        @Email String email,
        String nombre,
        String password
) {}