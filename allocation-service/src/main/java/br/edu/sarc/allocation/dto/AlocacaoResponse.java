package br.edu.sarc.allocation.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "Alocacao completa")
public record AlocacaoResponse(
        @Schema(description = "Identificador da alocacao", example = "1")
        Long id,

        @Schema(description = "Identificador do professor", example = "1")
        Long professorId,

        @Schema(description = "Nome do professor", example = "Professor Teste")
        String professor,

        @Schema(description = "Disciplina", example = "Engenharia de Software")
        String disciplina,

        @Schema(description = "Data", example = "2026-05-22")
        LocalDate data,

        @Schema(description = "Horario de inicio", example = "08:00")
        LocalTime horarioInicio,

        @Schema(description = "Horario de fim", example = "10:00")
        LocalTime horarioFim,

        @Schema(description = "Recursos associados")
        List<RecursoAlocadoResponse> recursos,

        @Schema(description = "Data de criacao do registro")
        LocalDateTime criadoEm
) {
}
