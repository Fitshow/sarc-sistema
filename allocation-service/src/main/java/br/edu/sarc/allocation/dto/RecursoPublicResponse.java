package br.edu.sarc.allocation.dto;

import br.edu.sarc.allocation.domain.TipoRecurso;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Recurso exibido na consulta publica de alocacoes")
public record RecursoPublicResponse(
        @Schema(description = "Nome do recurso", example = "Laboratorio 301")
        String recurso,

        @Schema(description = "Tipo do recurso", example = "LABORATORIO")
        TipoRecurso tipo,

        @Schema(description = "Localizacao fisica", example = "Predio 32")
        String localizacao
) {
}
