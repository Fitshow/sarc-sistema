package br.edu.sarc.schedule.controller;

import br.edu.sarc.schedule.config.SecurityConfig;
import br.edu.sarc.schedule.domain.TipoRecurso;
import br.edu.sarc.schedule.dto.ScheduleResponse;
import br.edu.sarc.schedule.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
@Import(SecurityConfig.class)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    @Test
    void deveConsultarGradePublicaSemAutenticacao() throws Exception {
        when(scheduleService.listarGrade(isNull(), isNull(), isNull(), isNull())).thenReturn(List.of(item()));

        mockMvc.perform(get("/api/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].disciplina").value("Engenharia de Software"))
                .andExpect(jsonPath("$[0].professor").value("Professor Teste"))
                .andExpect(jsonPath("$[0].recurso").value("Laboratorio 301"))
                .andExpect(jsonPath("$[0].tipoRecurso").value("LABORATORIO"))
                .andExpect(jsonPath("$[0].senhaHash").doesNotExist())
                .andExpect(jsonPath("$[0].senha_hash").doesNotExist());
    }

    @Test
    void deveFiltrarPorData() throws Exception {
        LocalDate data = LocalDate.of(2026, 5, 22);
        when(scheduleService.listarGrade(eq(data), isNull(), isNull(), isNull())).thenReturn(List.of(item()));

        mockMvc.perform(get("/api/schedules").param("data", "2026-05-22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data").value("2026-05-22"));
    }

    @Test
    void deveFiltrarPorProfessor() throws Exception {
        when(scheduleService.listarGrade(isNull(), eq(1L), isNull(), isNull())).thenReturn(List.of(item()));

        mockMvc.perform(get("/api/schedules").param("professor", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].professor").value("Professor Teste"));
    }

    @Test
    void deveFiltrarPorDisciplina() throws Exception {
        when(scheduleService.listarGrade(isNull(), isNull(), eq("Software"), isNull())).thenReturn(List.of(item()));

        mockMvc.perform(get("/api/schedules").param("disciplina", "Software"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].disciplina").value("Engenharia de Software"));
    }

    @Test
    void deveFiltrarPorRecurso() throws Exception {
        when(scheduleService.listarGrade(isNull(), isNull(), isNull(), eq(10L))).thenReturn(List.of(item()));

        mockMvc.perform(get("/api/schedules").param("recurso", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recurso").value("Laboratorio 301"));
    }

    private ScheduleResponse item() {
        return new ScheduleResponse(
                "Engenharia de Software",
                "Professor Teste",
                LocalDate.of(2026, 5, 22),
                LocalTime.of(8, 0),
                LocalTime.of(10, 0),
                "Laboratorio 301",
                TipoRecurso.LABORATORIO,
                "Predio 32"
        );
    }
}
