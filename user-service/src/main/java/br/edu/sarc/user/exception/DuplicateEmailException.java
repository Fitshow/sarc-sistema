package br.edu.sarc.user.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("Ja existe usuario cadastrado com o e-mail: " + email);
    }
}
