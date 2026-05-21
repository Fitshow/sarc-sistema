package br.edu.sarc.resource.controller;

import br.edu.sarc.resource.config.SecurityConfig;
import br.edu.sarc.resource.domain.TipoRecurso;
import br.edu.sarc.resource.dto.RecursoResponse;
import br.edu.sarc.resource.service.RecursoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecursoController.class)
@Import(SecurityConfig.class)
class RecursoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecursoService recursoService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @Test
    void deveListarRecursosAtivosPublicamente() throws Exception {
        when(recursoService.listarAtivosPublicamente()).thenReturn(List.of(
                new RecursoResponse(1L, "Laboratorio 301", TipoRecurso.LABORATORIO, "301", "Predio 32", true, LocalDateTime.now()),
                new RecursoResponse(2L, "Sala 401", TipoRecurso.SALA, "401", "Predio 32", true, LocalDateTime.now())
        ));

        mockMvc.perform(get("/api/resources/public"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Laboratorio 301"))
                .andExpect(jsonPath("$[*].ativo", everyItem(is(true))));
    }

    @Test
    void naoDeveListarRecursoInativoPublicamente() throws Exception {
        when(recursoService.listarAtivosPublicamente()).thenReturn(List.of(
                new RecursoResponse(1L, "Laboratorio 301", TipoRecurso.LABORATORIO, "301", "Predio 32", true, LocalDateTime.now())
        ));

        mockMvc.perform(get("/api/resources/public"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].nome", not(everyItem(is("Projetor Inativo")))))
                .andExpect(jsonPath("$[*].ativo", everyItem(is(true))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveCriarRecursoComoAdmin() throws Exception {
        when(recursoService.criar(any())).thenReturn(
                new RecursoResponse(3L, "Projetor 01", TipoRecurso.EQUIPAMENTO, null, "Almoxarifado", true, LocalDateTime.now())
        );

        String body = """
                {
                  "nome": "Projetor 01",
                  "tipo": "EQUIPAMENTO",
                  "localizacao": "Almoxarifado",
                  "ativo": true
                }
                """;

        mockMvc.perform(post("/api/resources")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/resources/3"))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nome").value("Projetor 01"))
                .andExpect(jsonPath("$.ativo").value(true));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deveDesativarRecursoComoAdmin() throws Exception {
        when(recursoService.desativar(eq(1L))).thenReturn(
                new RecursoResponse(1L, "Laboratorio 301", TipoRecurso.LABORATORIO, "301", "Predio 32", false, LocalDateTime.now())
        );

        mockMvc.perform(patch("/api/resources/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ativo").value(false));
    }
}
