package br.edu.sarc.schedule.dto;

import br.edu.sarc.schedule.domain.TipoRecurso;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Recurso disponivel para filtro publico")
public record ResourceFilterResponse(
        @Schema(description = "Identificador do recurso", example = "1")
        Long id,

        @Schema(description = "Nome do recurso", example = "Laboratorio 301")
        String nome,

        @Schema(description = "Tipo do recurso", example = "LABORATORIO")
        TipoRecurso tipoRecurso,

        @Schema(description = "Localizacao", example = "Predio 32")
        String localizacao
) {
}
