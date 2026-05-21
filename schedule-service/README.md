# Schedule Service

Microservico base para horarios, disponibilidade e consultas de agenda.

## Responsabilidade

Consolida a visualizacao publica da grade de horarios do SARC para o front-end.

Neste momento, o servico usa uma leitura consolidada simplificada diretamente nas tabelas relacionais:

- `alocacao`
- `alocacao_recurso`
- `recurso`
- `usuario`

Os servicos de dominio continuam responsaveis pelas regras de escrita:

- `allocation-service`: cria, altera e remove alocacoes
- `resource-service`: gerencia recursos e status ativo/inativo
- `user-service`: gerencia professores e administradores

O `schedule-service` nao cria usuarios publicos. Visitantes acessam a grade de forma anonima.

## Endpoints Publicos

- `GET /api/schedules`
- `GET /api/schedules/resources`
- `GET /api/schedules/professors`

Filtros da grade:

- `data`
- `professor`
- `disciplina`
- `recurso`

## Dados Expostos

A grade publica retorna apenas:

- disciplina
- professor
- data
- horarioInicio
- horarioFim
- recurso
- tipoRecurso
- localizacao

Nenhum dado sensivel, como `senha_hash`, e exposto.

Swagger/OpenAPI:

- `/swagger-ui.html`
- `/v3/api-docs`
