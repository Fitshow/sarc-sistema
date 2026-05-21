package br.edu.sarc.resource.mapper;

import br.edu.sarc.resource.domain.Recurso;
import br.edu.sarc.resource.dto.RecursoResponse;

public final class RecursoMapper {

    private RecursoMapper() {
    }

    public static RecursoResponse toResponse(Recurso recurso) {
        return new RecursoResponse(
                recurso.getId(),
                recurso.getNome(),
                recurso.getTipo(),
                recurso.getNumeroSala(),
                recurso.getLocalizacao(),
                recurso.isAtivo(),
                recurso.getCriadoEm()
        );
    }
}
