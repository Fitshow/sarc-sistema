package br.edu.sarc.user.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(Long id) {
        super("Usuario nao encontrado: " + id);
    }
}
