package br.edu.sarc.schedule.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Professor disponivel para filtro publico")
public record ProfessorFilterResponse(
        @Schema(description = "Identificador do professor", example = "1")
        Long id,

        @Schema(description = "Nome do professor", example = "Professor Teste")
        String nome
) {
}
