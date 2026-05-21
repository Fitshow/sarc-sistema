package br.edu.sarc.allocation.mapper;

import br.edu.sarc.allocation.domain.Alocacao;
import br.edu.sarc.allocation.domain.Recurso;
import br.edu.sarc.allocation.dto.AlocacaoPublicResponse;
import br.edu.sarc.allocation.dto.AlocacaoResponse;
import br.edu.sarc.allocation.dto.RecursoAlocadoResponse;
import br.edu.sarc.allocation.dto.RecursoPublicResponse;

import java.util.Comparator;
import java.util.List;

public final class AlocacaoMapper {

    private AlocacaoMapper() {
    }

    public static AlocacaoResponse toResponse(Alocacao alocacao) {
        return new AlocacaoResponse(
                alocacao.getId(),
                alocacao.getProfessor().getId(),
                alocacao.getProfessor().getNome(),
                alocacao.getDisciplina(),
                alocacao.getData(),
                alocacao.getHorarioInicio(),
                alocacao.getHorarioFim(),
                mapRecursos(alocacao),
                alocacao.getCriadoEm()
        );
    }

    public static AlocacaoPublicResponse toPublicResponse(Alocacao alocacao) {
        return new AlocacaoPublicResponse(
                alocacao.getDisciplina(),
                alocacao.getProfessor().getNome(),
                alocacao.getData(),
                alocacao.getHorarioInicio(),
                alocacao.getHorarioFim(),
                mapRecursosPublicos(alocacao)
        );
    }

    private static List<RecursoAlocadoResponse> mapRecursos(Alocacao alocacao) {
        return alocacao.getRecursos()
                .stream()
                .sorted(Comparator.comparing(Recurso::getNome))
                .map(recurso -> new RecursoAlocadoResponse(
                        recurso.getId(),
                        recurso.getNome(),
                        recurso.getTipo(),
                        recurso.getLocalizacao()
                ))
                .toList();
    }

    private static List<RecursoPublicResponse> mapRecursosPublicos(Alocacao alocacao) {
        return alocacao.getRecursos()
                .stream()
                .sorted(Comparator.comparing(Recurso::getNome))
                .map(recurso -> new RecursoPublicResponse(
                        recurso.getNome(),
                        recurso.getTipo(),
                        recurso.getLocalizacao()
                ))
                .toList();
    }
}
