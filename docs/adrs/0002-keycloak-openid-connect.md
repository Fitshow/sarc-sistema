# ADR 0002 - Keycloak com OpenID Connect

## Status

Aceita.

## Contexto

O SARC precisa autenticar professores e administradores sem criar autenticacao propria em cada microservico.

## Decisao

Usar Keycloak como provedor OpenID Connect, com JWT validado no API Gateway e nos servicos protegidos.

## Consequencias

- Roles reconhecidas: `PROFESSOR` e `ADMIN`.
- Visitantes publicos acessam apenas rotas publicas.
- O front-end usa o client publico `sarc-web`.
- Os servicos validam tokens sem armazenar sessoes.
