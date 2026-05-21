package br.edu.sarc.resource.dto;

import br.edu.sarc.resource.domain.TipoRecurso;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Recurso do SARC")
public record RecursoResponse(
        @Schema(description = "Identificador do recurso", example = "1")
        Long id,

        @Schema(description = "Nome do recurso", example = "Laboratorio 301")
        String nome,

        @Schema(description = "Tipo do recurso", example = "LABORATORIO")
        TipoRecurso tipo,

        @Schema(description = "Numero da sala", example = "301")
        String numeroSala,

        @Schema(description = "Localizacao fisica", example = "Predio 32")
        String localizacao,

        @Schema(description = "Indica se o recurso esta ativo", example = "true")
        boolean ativo,

        @Schema(description = "Data de criacao do registro")
        LocalDateTime criadoEm
) {
}
