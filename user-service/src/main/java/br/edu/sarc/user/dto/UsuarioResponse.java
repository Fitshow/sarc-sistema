package br.edu.sarc.user.dto;

import br.edu.sarc.user.domain.PerfilUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Usuario interno do SARC sem exposicao de senha ou hash")
public record UsuarioResponse(
        @Schema(description = "Identificador do usuario", example = "1")
        Long id,

        @Schema(description = "Nome completo", example = "Professor Teste")
        String nome,

        @Schema(description = "E-mail institucional", example = "professor@sarc.local")
        String email,

        @Schema(description = "Perfil interno", example = "PROFESSOR")
        PerfilUsuario perfil,

        @Schema(description = "Data de criacao do registro")
        LocalDateTime criadoEm
) {
}
