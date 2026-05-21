package br.edu.sarc.allocation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Resposta padrao de erro")
public record ErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
}
