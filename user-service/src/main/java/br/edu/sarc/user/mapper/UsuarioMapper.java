package br.edu.sarc.user.mapper;

import br.edu.sarc.user.domain.Usuario;
import br.edu.sarc.user.dto.UsuarioResponse;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.getCriadoEm()
        );
    }
}
