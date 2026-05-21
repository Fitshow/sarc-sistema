package br.edu.sarc.schedule.repository;

import br.edu.sarc.schedule.domain.Usuario;
import br.edu.sarc.schedule.dto.ProfessorFilterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("""
            select new br.edu.sarc.schedule.dto.ProfessorFilterResponse(
                u.id,
                u.nome
            )
            from Usuario u
            where u.perfil = br.edu.sarc.schedule.domain.PerfilUsuario.PROFESSOR
            order by u.nome asc
            """)
    List<ProfessorFilterResponse> listarProfessoresParaFiltro();
}
