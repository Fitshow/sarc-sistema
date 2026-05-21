package br.edu.sarc.resource.service;

import br.edu.sarc.resource.domain.Recurso;
import br.edu.sarc.resource.dto.RecursoRequest;
import br.edu.sarc.resource.dto.RecursoResponse;
import br.edu.sarc.resource.exception.RecursoNotFoundException;
import br.edu.sarc.resource.mapper.RecursoMapper;
import br.edu.sarc.resource.repository.RecursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Transactional(readOnly = true)
    public List<RecursoResponse> listarAtivosPublicamente() {
        return recursoRepository.findByAtivoTrueOrderByNomeAsc()
                .stream()
                .map(RecursoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RecursoResponse buscarAtivoPublicamente(Long id) {
        return RecursoMapper.toResponse(
                recursoRepository.findByIdAndAtivoTrue(id)
                        .orElseThrow(() -> new RecursoNotFoundException(id))
        );
    }

    @Transactional(readOnly = true)
    public List<RecursoResponse> listarTodos() {
        return recursoRepository.findAll()
                .stream()
                .map(RecursoMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RecursoResponse buscarPorId(Long id) {
        return RecursoMapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public RecursoResponse criar(RecursoRequest request) {
        boolean ativo = request.ativo() == null || request.ativo();
        Recurso recurso = new Recurso(
                request.nome(),
                request.tipo(),
                request.numeroSala(),
                request.localizacao(),
                ativo
        );

        return RecursoMapper.toResponse(recursoRepository.save(recurso));
    }

    @Transactional
    public RecursoResponse atualizar(Long id, RecursoRequest request) {
        Recurso recurso = buscarEntidade(id);
        recurso.setNome(request.nome());
        recurso.setTipo(request.tipo());
        recurso.setNumeroSala(request.numeroSala());
        recurso.setLocalizacao(request.localizacao());

        if (Boolean.TRUE.equals(request.ativo())) {
            recurso.ativar();
        } else if (Boolean.FALSE.equals(request.ativo())) {
            recurso.desativar();
        }

        return RecursoMapper.toResponse(recurso);
    }

    @Transactional
    public RecursoResponse ativar(Long id) {
        Recurso recurso = buscarEntidade(id);
        recurso.ativar();
        return RecursoMapper.toResponse(recurso);
    }

    @Transactional
    public RecursoResponse desativar(Long id) {
        Recurso recurso = buscarEntidade(id);
        recurso.desativar();
        return RecursoMapper.toResponse(recurso);
    }

    @Transactional
    public void remover(Long id) {
        if (!recursoRepository.existsById(id)) {
            throw new RecursoNotFoundException(id);
        }

        recursoRepository.deleteById(id);
    }

    private Recurso buscarEntidade(Long id) {
        return recursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNotFoundException(id));
    }
}
