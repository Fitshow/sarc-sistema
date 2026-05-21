package br.edu.sarc.schedule.domain;

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
import jakarta.persistence.Table;

import java.time.LocalDate;
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

    protected Alocacao() {
    }
}
