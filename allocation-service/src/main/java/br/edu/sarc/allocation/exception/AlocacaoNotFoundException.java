package br.edu.sarc.allocation.exception;

public class AlocacaoNotFoundException extends RuntimeException {

    public AlocacaoNotFoundException(Long id) {
        super("Alocacao nao encontrada: " + id);
    }
}
