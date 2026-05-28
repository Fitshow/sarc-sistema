package br.edu.sarc.user.service;

import br.edu.sarc.user.domain.PerfilUsuario;
import br.edu.sarc.user.domain.Usuario;
import br.edu.sarc.user.dto.UsuarioCreateRequest;
import br.edu.sarc.user.dto.UsuarioResponse;
import br.edu.sarc.user.dto.UsuarioUpdateRequest;
import br.edu.sarc.user.exception.DuplicateEmailException;
import br.edu.sarc.user.exception.UsuarioNotFoundException;
import br.edu.sarc.user.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UsuarioService service;

    @BeforeEach
    void setUp() {
        service = new UsuarioService(usuarioRepository, passwordEncoder);
    }

    // -------------------------------------------------------------------------
    // criar
    // -------------------------------------------------------------------------

    @Test
    void deveCriarUsuarioComSucesso() {
        when(usuarioRepository.existsByEmail("novo@sarc.local")).thenReturn(false);
        when(passwordEncoder.encode("senha123")).thenReturn("$2a$hash");
        when(usuarioRepository.save(any())).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            ReflectionTestUtils.setField(u, "id", 1L);
            return u;
        });

        UsuarioResponse response = service.criar(
                new UsuarioCreateRequest("Professor Novo", "novo@sarc.local", "senha123", PerfilUsuario.PROFESSOR)
        );

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.nome()).isEqualTo("Professor Novo");
        assertThat(response.email()).isEqualTo("novo@sarc.local");
        assertThat(response.perfil()).isEqualTo(PerfilUsuario.PROFESSOR);
    }

    @Test
    void deveBloquearCriacaoComEmailDuplicado() {
        when(usuarioRepository.existsByEmail("duplicado@sarc.local")).thenReturn(true);

        assertThatThrownBy(() -> service.criar(
                new UsuarioCreateRequest("Prof X", "duplicado@sarc.local", "senha", PerfilUsuario.PROFESSOR)
        ))
                .isInstanceOf(DuplicateEmailException.class);

        verify(usuarioRepository, never()).save(any());
    }

    // -------------------------------------------------------------------------
    // listarProfessores
    // -------------------------------------------------------------------------

    @Test
    void deveRetornarApenasUsuariosComPerfilProfessor() {
        Usuario professor = usuario(1L, "Prof A", "a@sarc.local", PerfilUsuario.PROFESSOR);
        when(usuarioRepository.findByPerfilOrderByNomeAsc(PerfilUsuario.PROFESSOR))
                .thenReturn(List.of(professor));

        List<UsuarioResponse> lista = service.listarProfessores();

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).perfil()).isEqualTo(PerfilUsuario.PROFESSOR);
    }

    // -------------------------------------------------------------------------
    // atualizar
    // -------------------------------------------------------------------------

    @Test
    void deveAtualizarDadosDoUsuario() {
        Usuario usuario = usuario(10L, "Antigo Nome", "antigo@sarc.local", PerfilUsuario.PROFESSOR);
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndIdNot("novo@sarc.local", 10L)).thenReturn(false);

        UsuarioResponse response = service.atualizar(10L,
                new UsuarioUpdateRequest("Novo Nome", "novo@sarc.local", null, PerfilUsuario.PROFESSOR));

        assertThat(response.nome()).isEqualTo("Novo Nome");
        assertThat(response.email()).isEqualTo("novo@sarc.local");
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void deveAtualizarSenhaQuandoInformada() {
        Usuario usuario = usuario(10L, "Prof", "prof@sarc.local", PerfilUsuario.PROFESSOR);
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndIdNot("prof@sarc.local", 10L)).thenReturn(false);
        when(passwordEncoder.encode("novaSenha")).thenReturn("$2a$novoHash");

        service.atualizar(10L,
                new UsuarioUpdateRequest("Prof", "prof@sarc.local", "novaSenha", PerfilUsuario.PROFESSOR));

        verify(passwordEncoder).encode("novaSenha");
        assertThat(usuario.getSenhaHash()).isEqualTo("$2a$novoHash");
    }

    @Test
    void deveIgnorarSenhaEmBrancoNaAtualizacao() {
        Usuario usuario = usuario(10L, "Prof", "prof@sarc.local", PerfilUsuario.PROFESSOR);
        String hashOriginal = usuario.getSenhaHash();
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndIdNot("prof@sarc.local", 10L)).thenReturn(false);

        service.atualizar(10L,
                new UsuarioUpdateRequest("Prof", "prof@sarc.local", "   ", PerfilUsuario.PROFESSOR));

        verify(passwordEncoder, never()).encode(anyString());
        assertThat(usuario.getSenhaHash()).isEqualTo(hashOriginal);
    }

    @Test
    void deveBloquearAtualizacaoComEmailJaUsadoPorOutroUsuario() {
        Usuario usuario = usuario(10L, "Prof", "prof@sarc.local", PerfilUsuario.PROFESSOR);
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByEmailAndIdNot("duplicado@sarc.local", 10L)).thenReturn(true);

        assertThatThrownBy(() -> service.atualizar(10L,
                new UsuarioUpdateRequest("Prof", "duplicado@sarc.local", null, PerfilUsuario.PROFESSOR)))
                .isInstanceOf(DuplicateEmailException.class);
    }

    // -------------------------------------------------------------------------
    // remover
    // -------------------------------------------------------------------------

    @Test
    void deveRemoverUsuarioExistente() {
        when(usuarioRepository.existsById(5L)).thenReturn(true);

        service.remover(5L);

        verify(usuarioRepository).deleteById(5L);
    }

    @Test
    void deveBloquearRemocaoDeUsuarioInexistente() {
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> service.remover(99L))
                .isInstanceOf(UsuarioNotFoundException.class);

        verify(usuarioRepository, never()).deleteById(any());
    }

    // -------------------------------------------------------------------------
    // helpers
    // -------------------------------------------------------------------------

    private Usuario usuario(Long id, String nome, String email, PerfilUsuario perfil) {
        Usuario u = new Usuario(nome, email, "$2a$hashPadrao", perfil);
        ReflectionTestUtils.setField(u, "id", id);
        return u;
    }
}
