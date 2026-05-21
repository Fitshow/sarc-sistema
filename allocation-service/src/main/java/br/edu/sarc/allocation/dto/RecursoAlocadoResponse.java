package br.edu.sarc.allocation.dto;

import br.edu.sarc.allocation.domain.TipoRecurso;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Recurso associado a uma alocacao")
public record RecursoAlocadoResponse(
        @Schema(description = "Identificador do recurso", example = "1")
        Long id,

        @Schema(description = "Nome do recurso", example = "Laboratorio 301")
        String nome,

        @Schema(description = "Tipo do recurso", example = "LABORATORIO")
        TipoRecurso tipo,

        @Schema(description = "Localizacao fisica", example = "Predio 32")
        String localizacao
) {
}
