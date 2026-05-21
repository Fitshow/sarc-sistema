package br.edu.sarc.resource.exception;

public class RecursoNotFoundException extends RuntimeException {

    public RecursoNotFoundException(Long id) {
        super("Recurso nao encontrado: " + id);
    }
}
