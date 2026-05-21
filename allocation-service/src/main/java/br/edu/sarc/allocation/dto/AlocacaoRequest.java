package br.edu.sarc.allocation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Schema(description = "Dados para criacao ou atualizacao de alocacao")
public record AlocacaoRequest(
        @Schema(description = "Professor vinculado. ADMIN pode informar outro professor; PROFESSOR deve usar o proprio id.", example = "1")
        Long professorId,

        @NotBlank
        @Size(max = 120)
        @Schema(description = "Disciplina da alocacao", example = "Engenharia de Software")
        String disciplina,

        @NotNull
        @FutureOrPresent
        @Schema(description = "Data da alocacao", example = "2026-05-22")
        LocalDate data,

        @NotNull
        @Schema(description = "Horario de inicio", example = "08:00")
        LocalTime horarioInicio,

        @NotNull
        @Schema(description = "Horario de fim", example = "10:00")
        LocalTime horarioFim,

        @NotEmpty
        @Schema(description = "Ids dos recursos alocados", example = "[1, 4]")
        List<Long> recursoIds
) {
}
