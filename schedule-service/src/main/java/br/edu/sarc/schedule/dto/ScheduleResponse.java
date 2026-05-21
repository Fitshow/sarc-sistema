package br.edu.sarc.schedule.dto;

import br.edu.sarc.schedule.domain.TipoRecurso;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

@Schema(description = "Item publico da grade de horarios")
public record ScheduleResponse(
        @Schema(description = "Disciplina", example = "Engenharia de Software")
        String disciplina,

        @Schema(description = "Nome do professor", example = "Professor Teste")
        String professor,

        @Schema(description = "Data da alocacao", example = "2026-05-22")
        LocalDate data,

        @Schema(description = "Horario de inicio", example = "08:00")
        LocalTime horarioInicio,

        @Schema(description = "Horario de fim", example = "10:00")
        LocalTime horarioFim,

        @Schema(description = "Nome do recurso", example = "Laboratorio 301")
        String recurso,

        @Schema(description = "Tipo do recurso", example = "LABORATORIO")
        TipoRecurso tipoRecurso,

        @Schema(description = "Localizacao do recurso", example = "Predio 32")
        String localizacao
) {
}
