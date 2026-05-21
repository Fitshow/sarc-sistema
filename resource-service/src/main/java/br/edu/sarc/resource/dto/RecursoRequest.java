package br.edu.sarc.resource.dto;

import br.edu.sarc.resource.domain.TipoRecurso;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para criacao ou atualizacao de recurso")
public record RecursoRequest(
        @NotBlank
        @Size(max = 100)
        @Schema(description = "Nome do recurso", example = "Laboratorio 301")
        String nome,

        @NotNull
        @Schema(description = "Tipo do recurso", example = "LABORATORIO")
        TipoRecurso tipo,

        @Size(max = 20)
        @Schema(description = "Numero da sala, quando aplicavel", example = "301")
        String numeroSala,

        @Size(max = 100)
        @Schema(description = "Localizacao fisica do recurso", example = "Predio 32")
        String localizacao,

        @Schema(description = "Indica se o recurso esta ativo", example = "true")
        Boolean ativo
) {
}
