package br.edu.sarc.resource.repository;

import br.edu.sarc.resource.domain.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecursoRepository extends JpaRepository<Recurso, Long> {

    List<Recurso> findByAtivoTrueOrderByNomeAsc();

    Optional<Recurso> findByIdAndAtivoTrue(Long id);
}
