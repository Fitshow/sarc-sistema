# Resource Service

Microservico base para salas, laboratorios e recursos computacionais.

## Responsabilidade

Gerencia os recursos alocaveis do SARC:

- Salas
- Laboratorios
- Computadores
- Projetores
- Equipamentos

Recursos inativos nao aparecem nas consultas publicas.

## Endpoints

Publicos:

- `GET /api/resources/public`
- `GET /api/resources/public/{id}`

Protegidos por role `ADMIN`:

- `GET /api/resources`
- `GET /api/resources/{id}`
- `POST /api/resources`
- `PUT /api/resources/{id}`
- `PATCH /api/resources/{id}/activate`
- `PATCH /api/resources/{id}/deactivate`
- `DELETE /api/resources/{id}`

Swagger/OpenAPI:

- `/swagger-ui.html`
- `/v3/api-docs`
