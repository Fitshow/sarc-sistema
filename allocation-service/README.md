# Allocation Service

Microservico base para solicitacoes e registros de alocacao de recursos.

## Responsabilidade

Gerencia alocacoes de professores para um ou mais recursos.

Regras principais:

- Recurso inativo nao pode ser alocado.
- Nao permite conflito de horario para o mesmo recurso, mesma data e horarios sobrepostos.
- Professor cria, atualiza e remove apenas alocacoes vinculadas a ele mesmo.
- ADMIN pode remover e atualizar qualquer alocacao.
- Visitante publico apenas consulta.

## Endpoints

Publicos:

- `GET /api/allocations/public`
- `GET /api/allocations/public/{id}`

Protegidos por role `PROFESSOR` ou `ADMIN`:

- `GET /api/allocations/my`
- `POST /api/allocations`
- `PUT /api/allocations/{id}`
- `DELETE /api/allocations/{id}`

Swagger/OpenAPI:

- `/swagger-ui.html`
- `/v3/api-docs`
