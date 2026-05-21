package br.edu.sarc.schedule.controller;

import br.edu.sarc.schedule.dto.ProfessorFilterResponse;
import br.edu.sarc.schedule.dto.ResourceFilterResponse;
import br.edu.sarc.schedule.dto.ScheduleResponse;
import br.edu.sarc.schedule.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@Tag(name = "Grade Publica", description = "Consultas publicas otimizadas para exibicao da grade do SARC")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    @Operation(
            summary = "Lista grade publica",
            description = "Consulta a grade consolidada com filtros opcionais por data, professor, disciplina e recurso. Nao exige autenticacao."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Grade encontrada",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ScheduleResponse.class)))
    )
    public List<ScheduleResponse> listarGrade(
            @Parameter(description = "Data da grade", example = "2026-05-22")
            @RequestParam(name = "data", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @Parameter(description = "Id do professor", example = "1")
            @RequestParam(name = "professor", required = false) Long professor,
            @Parameter(description = "Trecho da disciplina", example = "Software")
            @RequestParam(name = "disciplina", required = false) String disciplina,
            @Parameter(description = "Id do recurso", example = "1")
            @RequestParam(name = "recurso", required = false) Long recurso
    ) {
        return scheduleService.listarGrade(data, professor, disciplina, recurso);
    }

    @GetMapping("/resources")
    @Operation(
            summary = "Lista recursos para filtro",
            description = "Lista recursos ativos disponiveis para filtros publicos da grade."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Recursos encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResourceFilterResponse.class)))
    )
    public List<ResourceFilterResponse> listarRecursos() {
        return scheduleService.listarRecursos();
    }

    @GetMapping("/professors")
    @Operation(
            summary = "Lista professores para filtro",
            description = "Lista professores disponiveis para filtros publicos da grade. Nao retorna usuarios administradores ou dados sensiveis."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Professores encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProfessorFilterResponse.class)))
    )
    public List<ProfessorFilterResponse> listarProfessores() {
        return scheduleService.listarProfessores();
    }
}
