package br.edu.sarc.schedule.repository;

import br.edu.sarc.schedule.domain.Recurso;
import br.edu.sarc.schedule.dto.ResourceFilterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    @Query("""
            select new br.edu.sarc.schedule.dto.ResourceFilterResponse(
                r.id,
                r.nome,
                r.tipo,
                r.localizacao
            )
            from Recurso r
            where r.ativo = true
            order by r.nome asc
            """)
    List<ResourceFilterResponse> listarRecursosParaFiltro();
}
