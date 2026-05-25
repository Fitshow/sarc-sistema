package br.edu.sarc.schedule.service;

import br.edu.sarc.schedule.dto.ProfessorFilterResponse;
import br.edu.sarc.schedule.dto.ResourceFilterResponse;
import br.edu.sarc.schedule.dto.ScheduleResponse;
import br.edu.sarc.schedule.repository.RecursoRepository;
import br.edu.sarc.schedule.repository.ScheduleRepository;
import br.edu.sarc.schedule.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final RecursoRepository recursoRepository;
    private final UsuarioRepository usuarioRepository;

    public ScheduleService(
            ScheduleRepository scheduleRepository,
            RecursoRepository recursoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.scheduleRepository = scheduleRepository;
        this.recursoRepository = recursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<ScheduleResponse> listarGrade(LocalDate data, Long professorId, String disciplina, Long recursoId) {
        String disciplinaNormalizada = normalizarFiltro(disciplina);
        if (disciplinaNormalizada == null) {
            return scheduleRepository.buscarGradePublicaSemDisciplina(data, professorId, recursoId);
        }
        return scheduleRepository.buscarGradePublicaComDisciplina(data, professorId, disciplinaNormalizada, recursoId);
    }

    @Transactional(readOnly = true)
    public List<ResourceFilterResponse> listarRecursos() {
        return recursoRepository.listarRecursosParaFiltro();
    }

    @Transactional(readOnly = true)
    public List<ProfessorFilterResponse> listarProfessores() {
        return usuarioRepository.listarProfessoresParaFiltro();
    }

    private String normalizarFiltro(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
