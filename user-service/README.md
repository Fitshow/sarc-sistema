# User Service

Microservico base para usuarios, perfis e integracao com identidade institucional.

## Responsabilidade

Gerencia usuarios internos do SARC:

- Professores
- Administradores

Visitantes publicos podem consultar professores para filtros, mas nao sao usuarios autenticados.

## Endpoints

Publico:

- `GET /api/users/professors`: lista usuarios com perfil `PROFESSOR`

Protegidos por role `ADMIN`:

- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

Swagger/OpenAPI:

- `/swagger-ui.html`
- `/v3/api-docs`
