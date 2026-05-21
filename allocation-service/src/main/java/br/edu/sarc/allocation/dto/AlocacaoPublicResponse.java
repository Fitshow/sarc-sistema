package br.edu.sarc.allocation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "Alocacao publica sem dados internos sensiveis")
public record AlocacaoPublicResponse(
        @Schema(description = "Disciplina", example = "Engenharia de Software")
        String disciplina,

        @Schema(description = "Nome do professor", example = "Professor Teste")
        String professor,

        @Schema(description = "Data", example = "2026-05-22")
        LocalDate data,

        @Schema(description = "Horario de inicio", example = "08:00")
        LocalTime horarioInicio,

        @Schema(description = "Horario de fim", example = "10:00")
        LocalTime horarioFim,

        @Schema(description = "Recursos publicos da alocacao")
        List<RecursoPublicResponse> recursos
) {
}
