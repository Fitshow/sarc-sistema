package br.edu.sarc.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Map;

@Schema(description = "Resposta padrao de erro")
public record ErrorResponse(
        @Schema(description = "Momento em que o erro ocorreu")
        Instant timestamp,

        @Schema(description = "Status HTTP", example = "404")
        int status,

        @Schema(description = "Resumo do erro", example = "Not Found")
        String error,

        @Schema(description = "Mensagem legivel do erro", example = "Usuario nao encontrado")
        String message,

        @Schema(description = "Caminho da requisicao", example = "/api/users/1")
        String path,

        @Schema(description = "Erros de validacao por campo")
        Map<String, String> fieldErrors
) {
}
