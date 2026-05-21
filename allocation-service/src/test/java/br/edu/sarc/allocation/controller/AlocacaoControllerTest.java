package br.edu.sarc.allocation.controller;

import br.edu.sarc.allocation.config.SecurityConfig;
import br.edu.sarc.allocation.domain.TipoRecurso;
import br.edu.sarc.allocation.dto.AlocacaoPublicResponse;
import br.edu.sarc.allocation.dto.RecursoPublicResponse;
import br.edu.sarc.allocation.service.AlocacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlocacaoController.class)
@Import(SecurityConfig.class)
class AlocacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlocacaoService alocacaoService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    void deveListarPublicamenteSemAutenticacao() throws Exception {
        when(alocacaoService.buscarPublicamente(any(), isNull(), isNull(), isNull())).thenReturn(List.of(
                new AlocacaoPublicResponse(
                        "Engenharia de Software",
                        "Professor Teste",
                        LocalDate.now().plusDays(1),
                        LocalTime.of(8, 0),
                        LocalTime.of(10, 0),
                        List.of(new RecursoPublicResponse("Laboratorio 301", TipoRecurso.LABORATORIO, "Predio 32"))
                )
        ));

        mockMvc.perform(get("/api/allocations/public")
                        .param("data", LocalDate.now().plusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].disciplina").value("Engenharia de Software"))
                .andExpect(jsonPath("$[0].professor").value("Professor Teste"))
                .andExpect(jsonPath("$[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].recursos[0].id").doesNotExist())
                .andExpect(jsonPath("$[0].recursos[0].recurso").value("Laboratorio 301"))
                .andExpect(jsonPath("$[0].recursos[0].tipo").value("LABORATORIO"));
    }
}
