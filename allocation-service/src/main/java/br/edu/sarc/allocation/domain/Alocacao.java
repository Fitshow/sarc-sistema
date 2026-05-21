package br.edu.sarc.allocation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "alocacao")
public class Alocacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "professor_id", nullable = false)
    private Usuario professor;

    @Column(nullable = false, length = 120)
    private String disciplina;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "horario_inicio", nullable = false)
    private LocalTime horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private LocalTime horarioFim;

    @ManyToMany
    @JoinTable(
            name = "alocacao_recurso",
            joinColumns = @JoinColumn(name = "alocacao_id"),
            inverseJoinColumns = @JoinColumn(name = "recurso_id")
    )
    private Set<Recurso> recursos = new LinkedHashSet<>();

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    protected Alocacao() {
    }

    public Alocacao(Usuario professor, String disciplina, LocalDate data, LocalTime horarioInicio, LocalTime horarioFim, Set<Recurso> recursos) {
        this.professor = professor;
        this.disciplina = disciplina;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.recursos = new LinkedHashSet<>(recursos);
    }

    public Long getId() {
        return id;
    }

    public Usuario getProfessor() {
        return professor;
    }

    public void setProfessor(Usuario professor) {
        this.professor = professor;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public Set<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(Set<Recurso> recursos) {
        this.recursos = new LinkedHashSet<>(recursos);
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    @PrePersist
    void prePersist() {
        if (criadoEm == null) {
            criadoEm = LocalDateTime.now();
        }
    }
}
