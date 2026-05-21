package br.edu.sarc.allocation.security;

public record CurrentUser(String email, boolean admin, boolean professor) {
}
