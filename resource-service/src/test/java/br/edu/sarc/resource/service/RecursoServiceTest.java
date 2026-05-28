package br.edu.sarc.resource.service;

import br.edu.sarc.resource.domain.Recurso;
import br.edu.sarc.resource.domain.TipoRecurso;
import br.edu.sarc.resource.dto.RecursoRequest;
import br.edu.sarc.resource.dto.RecursoResponse;
import br.edu.sarc.resource.exception.RecursoNotFoundException;
import br.edu.sarc.resource.repository.RecursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecursoServiceTest {

    @Mock
    private RecursoRepository recursoRepository;

    private RecursoService service;

    @BeforeEach
    void setUp() {
        service = new RecursoService(recursoRepository);
    }

    // -------------------------------------------------------------------------
    // criar
    // -------------------------------------------------------------------------

    @Test
    void deveCriarRecursoComSucesso() {
        when(recursoRepository.save(any())).thenAnswer(inv -> {
            Recurso r = inv.getArgument(0);
            ReflectionTestUtils.setField(r, "id", 1L);
            return r;
        });

        RecursoResponse response = service.criar(
                new RecursoRequest("Laboratorio 301", TipoRecurso.LABORATORIO, "301", "Predio 32", true)
        );

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Laboratorio 301");
        assertThat(response.tipo()).isEqualTo(TipoRecurso.LABORATORIO);
        assertThat(response.ativo()).isTrue();
    }

    @Test
    void deveCriarRecursoAtivoQuandoCampoAtivoNulo() {
        when(recursoRepository.save(any())).thenAnswer(inv -> {
            Recurso r = inv.getArgument(0);
            ReflectionTestUtils.setField(r, "id", 2L);
            return r;
        });

        // ativo == null deve ser tratado como true
        RecursoResponse response = service.criar(
                new RecursoRequest("Projetor 01", TipoRecurso.EQUIPAMENTO, null, "Almoxarifado", null)
        );

        assertThat(response.ativo()).isTrue();
    }

    // -------------------------------------------------------------------------
    // listarAtivosPublicamente
    // -------------------------------------------------------------------------

    @Test
    void deveRetornarApenasRecursosAtivos() {
        Recurso ativo = recurso(1L, "Lab 301", TipoRecurso.LABORATORIO, true);
        when(recursoRepository.findByAtivoTrueOrderByNomeAsc()).thenReturn(List.of(ativo));

        List<RecursoResponse> lista = service.listarAtivosPublicamente();

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).ativo()).isTrue();
    }

    // -------------------------------------------------------------------------
    // desativar
    // -------------------------------------------------------------------------

    @Test
    void deveDesativarRecursoExistente() {
        Recurso recurso = recurso(1L, "Lab 301", TipoRecurso.LABORATORIO, true);
        when(recursoRepository.findById(1L)).thenReturn(Optional.of(recurso));

        RecursoResponse response = service.desativar(1L);

        assertThat(response.ativo()).isFalse();
    }

    @Test
    void deveBloquearDesativarRecursoInexistente() {
        when(recursoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.desativar(99L))
                .isInstanceOf(RecursoNotFoundException.class);
    }

    // -------------------------------------------------------------------------
    // remover
    // -------------------------------------------------------------------------

    @Test
    void deveRemoverRecursoExistente() {
        when(recursoRepository.existsById(3L)).thenReturn(true);

        service.remover(3L);

        verify(recursoRepository).deleteById(3L);
    }

    @Test
    void deveBloquearRemocaoDeRecursoInexistente() {
        when(recursoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.remover(99L))
                .isInstanceOf(RecursoNotFoundException.class);

        verify(recursoRepository, never()).deleteById(any());
    }

    // -------------------------------------------------------------------------
    // ativar
    // -------------------------------------------------------------------------

    @Test
    void deveAtivarRecursoInativo() {
        Recurso recurso = recurso(5L, "Projetor", TipoRecurso.EQUIPAMENTO, false);
        when(recursoRepository.findById(5L)).thenReturn(Optional.of(recurso));

        RecursoResponse response = service.ativar(5L);

        assertThat(response.ativo()).isTrue();
    }

    // -------------------------------------------------------------------------
    // helpers
    // -------------------------------------------------------------------------

    private Recurso recurso(Long id, String nome, TipoRecurso tipo, boolean ativo) {
        Recurso r = new Recurso(nome, tipo, null, "Local", ativo);
        ReflectionTestUtils.setField(r, "id", id);
        return r;
    }
}
