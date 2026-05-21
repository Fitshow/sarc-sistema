package br.edu.sarc.allocation.repository;

import br.edu.sarc.allocation.domain.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByIdIn(Collection<Long> ids);
}
