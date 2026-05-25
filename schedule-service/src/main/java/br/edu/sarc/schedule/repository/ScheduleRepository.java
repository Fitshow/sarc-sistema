package br.edu.sarc.schedule.repository;

import br.edu.sarc.schedule.domain.Alocacao;
import br.edu.sarc.schedule.dto.ScheduleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Alocacao, Long> {

    @Query("""
            select new br.edu.sarc.schedule.dto.ScheduleResponse(
                a.disciplina,
                p.nome,
                a.data,
                a.horarioInicio,
                a.horarioFim,
                r.nome,
                r.tipo,
                r.localizacao
            )
            from Alocacao a
            join a.professor p
            join a.recursos r
            where (:data is null or a.data = :data)
              and (:professorId is null or p.id = :professorId)
              and (:recursoId is null or r.id = :recursoId)
              and r.ativo = true
            order by a.data asc, a.horarioInicio asc, p.nome asc, r.nome asc
            """)
    List<ScheduleResponse> buscarGradePublicaSemDisciplina(
            @Param("data") LocalDate data,
            @Param("professorId") Long professorId,
            @Param("recursoId") Long recursoId
    );

    @Query("""
            select new br.edu.sarc.schedule.dto.ScheduleResponse(
                a.disciplina,
                p.nome,
                a.data,
                a.horarioInicio,
                a.horarioFim,
                r.nome,
                r.tipo,
                r.localizacao
            )
            from Alocacao a
            join a.professor p
            join a.recursos r
            where (:data is null or a.data = :data)
              and (:professorId is null or p.id = :professorId)
              and lower(a.disciplina) like lower(concat('%', :disciplina, '%'))
              and (:recursoId is null or r.id = :recursoId)
              and r.ativo = true
            order by a.data asc, a.horarioInicio asc, p.nome asc, r.nome asc
            """)
    List<ScheduleResponse> buscarGradePublicaComDisciplina(
            @Param("data") LocalDate data,
            @Param("professorId") Long professorId,
            @Param("disciplina") String disciplina,
            @Param("recursoId") Long recursoId
    );
}
