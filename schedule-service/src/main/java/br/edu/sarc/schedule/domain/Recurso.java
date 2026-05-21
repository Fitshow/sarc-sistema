package br.edu.sarc.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recurso")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoRecurso tipo;

    @Column(length = 100)
    private String localizacao;

    @Column(nullable = false)
    private boolean ativo;

    protected Recurso() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
